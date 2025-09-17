package com.lms.cases.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaseRequestDto {

    @NotBlank(message = "Case title is required")
    private String title;

    private String description;

    private Long advocateId;
}
