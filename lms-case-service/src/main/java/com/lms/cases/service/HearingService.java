package com.lms.cases.service;

import com.lms.cases.dto.HearingRequestDto;
import com.lms.cases.dto.HearingResponseDto;
import com.lms.cases.dto.HearingUpdateDto;

import java.util.List;
import java.util.Optional;

public interface HearingService {

    HearingResponseDto createHearing(HearingRequestDto hearing);

    Optional<HearingResponseDto> getHearingById(Long id);

    List<HearingResponseDto> getHearingsByCaseId(Long caseId);

    HearingResponseDto updateHearing(Long id, HearingUpdateDto hearingUpdate);

    void deleteHearing(Long id, Long deletedBy);

    /**
     * Checks if a given case belongs to the user (for CLIENT access control)
     *
     * @param caseId case ID
     * @param userId user ID
     * @return true if the case belongs to the user, false otherwise
     */
    boolean isHearingBelongsToUser(Long caseId, Long userId);

    /**
     * Get hearing by ID with user access control
     *
     * @param hearingId hearing ID
     * @param userId user ID
     * @param roles list of user roles
     * @return HearingResponseDto if accessible
     */
    HearingResponseDto getHearingByIdForUser(Long hearingId, Long userId, List<String> roles);

    /**
     * Get hearings for a case with user access control
     *
     * @param caseId case ID
     * @param userId user ID
     * @param roles list of user roles
     * @return list of accessible hearings
     */
    List<HearingResponseDto> getHearingsByCaseIdForUser(Long caseId, Long userId, List<String> roles);
}
