package com.lms.cases.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import com.lms.cases.enums.EventType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleResponseDto {
	private Long scheduleId;
	private Long caseId;
	private EventType eventType;
	private LocalDateTime dueDate;
	private String remarks;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
