package com.lms.cases.service;

import com.lms.cases.dto.CaseRequestDto;
import com.lms.cases.dto.CaseResponseDto;
import com.lms.cases.feign.dto.DocumentResponseDto;

import java.util.List;

public interface CaseService {

	// Create a new case
	CaseResponseDto createCase(CaseRequestDto requestDto);

	// Get case by ID
	CaseResponseDto getCaseById(Long caseId);

	// Update case details
	CaseResponseDto updateCase(Long caseId, CaseRequestDto requestDto);

	// Change case status (OPEN -> CLOSED or vice versa)
	CaseResponseDto changeCaseStatus(Long caseId, String status);

	// Delete or soft-delete a case
	void deleteCase(Long caseId, Long deletedBy);

	// List all cases (optionally filter by status)
	List<CaseResponseDto> getAllCases(String status);
	
	List<CaseResponseDto> getCasesByUserId(Long userId);
	
	
	// feign client to get documents by case id
	List<DocumentResponseDto> getDocumentsByCaseId(Long caseId);
//
//	// List all cases
//	List<CaseResponseDto> getAllCases();
}
