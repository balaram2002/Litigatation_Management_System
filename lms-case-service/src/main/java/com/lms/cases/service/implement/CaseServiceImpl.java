package com.lms.cases.service.implement;

import com.lms.cases.dto.CaseRequestDto;
import com.lms.cases.dto.CaseResponseDto;
import com.lms.cases.dto.HearingResponseDto;
import com.lms.cases.dto.ScheduleResponseDto;
import com.lms.cases.dto.CaseAdvocateResponseDto;
import com.lms.cases.entity.Case;
import com.lms.cases.enums.Status;
import com.lms.cases.feign.DocumentClient;
import com.lms.cases.feign.dto.DocumentResponseDto;
import com.lms.cases.mapper.CaseMapper;
import com.lms.cases.mapper.HearingMapper;
import com.lms.cases.mapper.ScheduleMapper;
import com.lms.cases.mapper.CaseAdvocateMapper;
import com.lms.cases.repository.CaseRepository;
import com.lms.cases.repository.HearingRepository;
import com.lms.cases.repository.ScheduleRepository;
import com.lms.cases.repository.CaseAdvocateRepository;
import com.lms.cases.service.CaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CaseServiceImpl implements CaseService {

	private final CaseRepository caseRepository;
	private final HearingRepository hearingRepository;
	private final ScheduleRepository scheduleRepository;
	private final CaseAdvocateRepository caseAdvocateRepository;
	private final DocumentClient documentClient; // Uncomment when Feign client is set up

	
	/**
	 * Create a new case.
	 * @param requestDto CaseRequestDto containing case details.
	 * @return CaseResponseDto with enriched data.
	 */
	@Override
	public CaseResponseDto createCase(CaseRequestDto requestDto) {
		Case caseEntity = CaseMapper.toEntity(requestDto);
		caseRepository.save(caseEntity);
		return enrichCaseResponse(caseEntity);
	}

	@Override
	public CaseResponseDto getCaseById(Long caseId) {
		Case caseEntity = caseRepository.findById(caseId).orElseThrow(() -> new RuntimeException("Case not found"));
		return enrichCaseResponse(caseEntity);
	}

	@Override
	public CaseResponseDto updateCase(Long caseId, CaseRequestDto requestDto) {
		Case caseEntity = caseRepository.findById(caseId).orElseThrow(() -> new RuntimeException("Case not found"));

		caseEntity.setTitle(requestDto.getTitle());
		caseEntity.setDescription(requestDto.getDescription());
		caseEntity.setAdvocateId(requestDto.getAdvocateId());

		caseRepository.save(caseEntity);
		return enrichCaseResponse(caseEntity);
	}

	@Override
	public CaseResponseDto changeCaseStatus(Long caseId, String status) {
		Case caseEntity = caseRepository.findById(caseId).orElseThrow(() -> new RuntimeException("Case not found"));

		caseEntity.setStatus(Status.valueOf(status.toUpperCase()));
		caseRepository.save(caseEntity);

		return enrichCaseResponse(caseEntity);
	}

	@Override
	public void deleteCase(Long caseId, Long deletedBy) {
		Case caseEntity = caseRepository.findById(caseId).orElseThrow(() -> new RuntimeException("Case not found"));

		caseEntity.setDeletedBy(deletedBy);
		caseEntity.setDeletedAt(LocalDateTime.now());
		caseRepository.save(caseEntity);
	}

	@Override
	public List<CaseResponseDto> getAllCases(String status) {
		List<Case> cases;
		if (status != null) {
			cases = caseRepository.findByStatus(Status.valueOf(status.toUpperCase()));
		} else {
			cases = caseRepository.findAllByDeletedAtIsNull();
		}
		return cases.stream().map(this::enrichCaseResponse).collect(Collectors.toList());
	}

	/**
	 * Helper to build CaseResponseDto with hearings, schedules, and advocates.
	 */
	private CaseResponseDto enrichCaseResponse(Case caseEntity) {
		CaseResponseDto dto = CaseMapper.toDto(caseEntity);

		// fetch related hearings
		List<HearingResponseDto> hearings = hearingRepository.findByCaseId(caseEntity.getCaseId()).stream()
				.map(HearingMapper::toResponseDto).collect(Collectors.toList());
		dto.setHearings(hearings);

		// fetch related schedules
		List<ScheduleResponseDto> schedules = scheduleRepository.findByCaseId(caseEntity.getCaseId()).stream()
				.map(ScheduleMapper::toResponseDto).collect(Collectors.toList());
		dto.setSchedules(schedules);

		// fetch related advocates
		List<CaseAdvocateResponseDto> advocates = caseAdvocateRepository.findByCaseId(caseEntity.getCaseId()).stream()
				.map(CaseAdvocateMapper::toDto).collect(Collectors.toList());
		dto.setAdvocates(advocates);

		return dto;
	}

	@Override
	public List<CaseResponseDto> getCasesByUserId(Long userId) {
		
		List<Case> cases = caseRepository.findByAdvocateId(userId);
		return cases.stream().map(this::enrichCaseResponse).collect(Collectors.toList());
		
	}

	
	// Feign client to get documents by case id
	@Override
	public List<DocumentResponseDto> getDocumentsByCaseId(Long caseId) {
		// This method would typically call another microservice via Feign client to fetch documents.
		List<DocumentResponseDto> documents = documentClient.getDocumentsByCaseId(caseId);
		return documents;
	}
}
