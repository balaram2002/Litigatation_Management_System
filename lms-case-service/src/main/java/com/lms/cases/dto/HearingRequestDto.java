package com.lms.cases.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
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
public class HearingRequestDto {
	@NotNull
	private Long caseId;

	private Long advocateId;
	@NotNull
	private LocalDateTime date;

	private String location;
	private String notes;
}
