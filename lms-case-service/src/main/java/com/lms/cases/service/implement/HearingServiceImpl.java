package com.lms.cases.service.implement;

import com.lms.cases.dto.HearingRequestDto;
import com.lms.cases.dto.HearingResponseDto;
import com.lms.cases.dto.HearingUpdateDto;
import com.lms.cases.entity.Hearing;
import com.lms.cases.mapper.HearingMapper;
import com.lms.cases.repository.HearingRepository;
import com.lms.cases.service.HearingService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class HearingServiceImpl implements HearingService {

    private final HearingRepository hearingRepository;

    @Override
    public HearingResponseDto createHearing(HearingRequestDto hearingRequest) {
        if (hearingRequest == null) throw new IllegalArgumentException("Hearing request cannot be null");
        Hearing hearing = HearingMapper.toEntity(hearingRequest);
        Hearing savedHearing = hearingRepository.save(hearing);
        return HearingMapper.toResponseDto(savedHearing);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<HearingResponseDto> getHearingById(Long id) {
        if (id == null) throw new IllegalArgumentException("Hearing ID cannot be null");
        return hearingRepository.findById(id).map(HearingMapper::toResponseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HearingResponseDto> getHearingsByCaseId(Long caseId) {
        if (caseId == null) throw new IllegalArgumentException("Case ID cannot be null");
        return hearingRepository.findByCaseId(caseId)
                .stream()
                .map(HearingMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public HearingResponseDto updateHearing(Long id, HearingUpdateDto hearingUpdate) {
        if (id == null || hearingUpdate == null)
            throw new IllegalArgumentException("Hearing ID and update data cannot be null");

        Hearing hearing = hearingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hearing not found with ID: " + id));

        if (hearingUpdate.getCaseId() != null) hearing.setCaseId(hearingUpdate.getCaseId());
        if (hearingUpdate.getAdvocateId() != null) hearing.setAdvocateId(hearingUpdate.getAdvocateId());
        if (hearingUpdate.getDate() != null) hearing.setDate(hearingUpdate.getDate());
        if (hearingUpdate.getLocation() != null) hearing.setLocation(hearingUpdate.getLocation());
        if (hearingUpdate.getNotes() != null) hearing.setNotes(hearingUpdate.getNotes());

        return HearingMapper.toResponseDto(hearingRepository.save(hearing));
    }

    @Override
    public void deleteHearing(Long id, Long deletedBy) {
        if (id == null) throw new IllegalArgumentException("Hearing ID cannot be null");

        Hearing hearing = hearingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hearing not found with ID: " + id));

        hearing.setDeletedBy(deletedBy); // soft delete
        hearingRepository.save(hearing);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isHearingBelongsToUser(Long caseId, Long userId) {
        if (caseId == null || userId == null)
            throw new IllegalArgumentException("Case ID and User ID cannot be null");

        return hearingRepository.existsByCaseIdAndClientId(caseId, userId);
    }

    /**
     * Get hearing by ID and check access for CLIENT.
     */
    @Override
    @Transactional(readOnly = true)
    public HearingResponseDto getHearingByIdForUser(Long hearingId, Long userId, List<String> roles) {
        Hearing hearing = hearingRepository.findById(hearingId)
                .orElseThrow(() -> new EntityNotFoundException("Hearing not found with ID: " + hearingId));

        if (roles.contains("ROLE_CLIENT") && !isHearingBelongsToUser(hearing.getCaseId(), userId)) {
            throw new AccessDeniedException("Access denied: cannot view hearings of other users");
        }

        return HearingMapper.toResponseDto(hearing);
    }

    /**
     * Get hearings for a case and filter by CLIENT ownership.
     */
    @Override
    @Transactional(readOnly = true)
    public List<HearingResponseDto> getHearingsByCaseIdForUser(Long caseId, Long userId, List<String> roles) {
        List<HearingResponseDto> hearings = getHearingsByCaseId(caseId);

        if (roles.contains("ROLE_CLIENT")) {
            hearings = hearings.stream()
                    .filter(h -> isHearingBelongsToUser(h.getCaseId(), userId))
                    .collect(Collectors.toList());
        }

        return hearings;
    }
}
