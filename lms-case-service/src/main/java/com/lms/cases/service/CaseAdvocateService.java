package com.lms.cases.service;

import com.lms.cases.dto.CaseAdvocateRequestDto;
import com.lms.cases.dto.CaseAdvocateResponseDto;

import java.util.List;

public interface CaseAdvocateService {

    CaseAdvocateResponseDto assignAdvocate(CaseAdvocateRequestDto dto);

    CaseAdvocateResponseDto getCaseAdvocateById(Long id);

    List<CaseAdvocateResponseDto> getAdvocatesByCaseId(Long caseId);

    CaseAdvocateResponseDto updateCaseAdvocateRole(Long id, CaseAdvocateRequestDto dto);

    void unassignAdvocate(Long id);
}
