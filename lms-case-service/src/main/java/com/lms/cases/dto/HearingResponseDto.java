package com.lms.cases.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class HearingResponseDto {
	private Long hearingId;
	private Long caseId;
	private Long advocateId;
	private LocalDateTime date;
	private String location;
	private String notes;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
