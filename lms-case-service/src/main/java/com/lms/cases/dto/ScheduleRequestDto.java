package com.lms.cases.dto;

import java.time.LocalDateTime;

import com.lms.cases.enums.EventType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleRequestDto {
    private Long caseId;
    private EventType eventType;
    private LocalDateTime dueDate;
    private String remarks;
}
