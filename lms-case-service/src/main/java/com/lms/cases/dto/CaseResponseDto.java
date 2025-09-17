package com.lms.cases.dto;

import com.lms.cases.enums.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

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
