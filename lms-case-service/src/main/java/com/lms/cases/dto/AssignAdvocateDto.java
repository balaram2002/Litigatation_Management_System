package com.lms.cases.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssignAdvocateDto {

    @NotNull(message = "Case ID is required")
    private Long caseId;

    @NotNull(message = "Advocate ID is required")
    private Long advocateId;
}
