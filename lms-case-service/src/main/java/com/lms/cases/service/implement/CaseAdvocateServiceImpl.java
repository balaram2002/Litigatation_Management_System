package com.lms.cases.service.implement;

import com.lms.cases.dto.CaseAdvocateRequestDto;
import com.lms.cases.dto.CaseAdvocateResponseDto;
import com.lms.cases.entity.CaseAdvocate;
import com.lms.cases.mapper.CaseAdvocateMapper;
import com.lms.cases.repository.CaseAdvocateRepository;
import com.lms.cases.service.CaseAdvocateService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CaseAdvocateServiceImpl implements CaseAdvocateService {

	private final CaseAdvocateRepository caseAdvocateRepository;

	@Override
	public CaseAdvocateResponseDto assignAdvocate(CaseAdvocateRequestDto dto) {
		CaseAdvocate entity = CaseAdvocateMapper.toEntity(dto);
		CaseAdvocate saved = caseAdvocateRepository.save(entity);
		return CaseAdvocateMapper.toDto(saved);
	}

	@Override
	@Transactional(readOnly = true)
	public CaseAdvocateResponseDto getCaseAdvocateById(Long id) {
		CaseAdvocate entity = caseAdvocateRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("CaseAdvocate not found with ID: " + id));
		return CaseAdvocateMapper.toDto(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public List<CaseAdvocateResponseDto> getAdvocatesByCaseId(Long caseId) {
		List<CaseAdvocate> list = caseAdvocateRepository.findByCaseId(caseId);
		return list.stream().map(CaseAdvocateMapper::toDto).collect(Collectors.toList());
	}

	@Override
	public CaseAdvocateResponseDto updateCaseAdvocateRole(Long id, CaseAdvocateRequestDto dto) {
		CaseAdvocate entity = caseAdvocateRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("CaseAdvocate not found with ID: " + id));
		if (dto.getRoleInCase() != null) {
			entity.setRoleInCase(dto.getRoleInCase());
		}
		if (dto.getCaseId() != null) {
			entity.setCaseId(dto.getCaseId());
		}
		if (dto.getAdvocateId() != null) {
			entity.setAdvocateId(dto.getAdvocateId());
		}
		return CaseAdvocateMapper.toDto(caseAdvocateRepository.save(entity));
	}

	@Override
	public void unassignAdvocate(Long id) {
		CaseAdvocate entity = caseAdvocateRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("CaseAdvocate not found with ID: " + id));
		entity.setUnassignedAt(LocalDateTime.now());
		caseAdvocateRepository.save(entity);
	}
}
