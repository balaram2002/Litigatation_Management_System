package com.lms.user.feign.dto;

import java.time.LocalDateTime;
import java.util.List;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CaseResponseDto {

    private Long caseId;
    private String title;
    private String description;
    private Status status;
    private Long advocateId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // New: Nested data
    private List<HearingResponseDto> hearings;
    private List<ScheduleResponseDto> schedules;
    private List<CaseAdvocateResponseDto> advocates;
}
