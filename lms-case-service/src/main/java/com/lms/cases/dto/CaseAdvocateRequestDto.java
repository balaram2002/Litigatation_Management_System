package com.lms.cases.dto;

import com.lms.cases.enums.AdvocateRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CaseAdvocateRequestDto {
    private Long caseId;
    private Long advocateId;
    private AdvocateRole roleInCase;
}
