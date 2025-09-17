package com.lms.cases.service.implement;

import com.lms.cases.dto.ScheduleRequestDto;
import com.lms.cases.dto.ScheduleResponseDto;
import com.lms.cases.entity.Schedule;
import com.lms.cases.mapper.ScheduleMapper;
import com.lms.cases.repository.ScheduleRepository;
import com.lms.cases.service.ScheduleService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

	private final ScheduleRepository scheduleRepository;

	@Override
	@Transactional
	public ScheduleResponseDto createSchedule(ScheduleRequestDto dto, Long userId, List<String> roles) {
		if (!(roles.contains("ROLE_ADMIN") || roles.contains("ROLE_ADVOCATE"))) {
			throw new AccessDeniedException("Only Admin or Advocate can create schedules");
		}

		Schedule schedule = ScheduleMapper.toEntity(dto);
		return ScheduleMapper.toResponseDto(scheduleRepository.save(schedule));
	}

	@Override
	@Transactional
	public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto dto, Long userId, List<String> roles) {
		if (!(roles.contains("ROLE_ADMIN") || roles.contains("ROLE_ADVOCATE"))) {
			throw new AccessDeniedException("Only Admin or Advocate can update schedules");
		}

		Schedule schedule = scheduleRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Schedule not found with ID: " + id));

		schedule.setCaseId(dto.getCaseId());
		schedule.setEventType(dto.getEventType());
		schedule.setDueDate(dto.getDueDate());
		schedule.setRemarks(dto.getRemarks());

		return ScheduleMapper.toResponseDto(scheduleRepository.save(schedule));
	}

	@Override
	@Transactional
	public void deleteSchedule(Long id, Long userId, List<String> roles) {
		if (!roles.contains("ROLE_ADMIN")) {
			throw new AccessDeniedException("Only Admin can delete schedules");
		}
		scheduleRepository.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public ScheduleResponseDto getScheduleById(Long id, Long userId, List<String> roles) {
		Schedule schedule = scheduleRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Schedule not found with ID: " + id));

		// Access control
		if (roles.contains("ROLE_ADMIN")) {
			return ScheduleMapper.toResponseDto(schedule);
		}

		if (roles.contains("ROLE_ADVOCATE")) {
			// TODO: check if advocate is assigned to this case
			return ScheduleMapper.toResponseDto(schedule);
		}

		if (roles.contains("ROLE_CLIENT")) {
			// TODO: check if client is part of this case
			// Example: call CaseService/UserService if mapping exists
			return ScheduleMapper.toResponseDto(schedule);
		}

		throw new AccessDeniedException("Access denied");
	}

	@Override
	@Transactional(readOnly = true)
	public List<ScheduleResponseDto> getSchedulesByCase(Long caseId, Long userId, List<String> roles) {
		List<Schedule> schedules = scheduleRepository.findByCaseId(caseId);

		if (roles.contains("ROLE_ADMIN")) {
			return schedules.stream().map(ScheduleMapper::toResponseDto).collect(Collectors.toList());
		}

		if (roles.contains("ROLE_ADVOCATE")) {
			// TODO: validate advocate-case mapping
			return schedules.stream().map(ScheduleMapper::toResponseDto).collect(Collectors.toList());
		}

		if (roles.contains("ROLE_CLIENT")) {
			// TODO: validate client-case mapping
			return schedules.stream().map(ScheduleMapper::toResponseDto).collect(Collectors.toList());
		}

		throw new AccessDeniedException("Access denied");
	}
}
