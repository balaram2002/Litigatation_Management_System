package com.lms.cases.dto;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotNull;
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
public class HearingUpdateDto {

	@NotNull(message = "Hearing ID is required")
	private Long hearingId;

	private Long caseId;
	private Long advocateId;
	private LocalDateTime date;
	private String location;
	private String notes;
}
