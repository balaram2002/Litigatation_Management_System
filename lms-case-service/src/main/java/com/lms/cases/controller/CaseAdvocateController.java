package com.lms.cases.controller;

import com.lms.cases.dto.ApiResponse;
import com.lms.cases.dto.CaseAdvocateRequestDto;
import com.lms.cases.dto.CaseAdvocateResponseDto;
import com.lms.cases.service.CaseAdvocateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/case-advocates")
@RequiredArgsConstructor
public class CaseAdvocateController {

	private final CaseAdvocateService caseAdvocateService;

	@PreAuthorize("hasAnyRole('ADMIN','ADVOCATE')")
	@PostMapping
	public ResponseEntity<ApiResponse<CaseAdvocateResponseDto>> assignAdvocate(
			@RequestBody CaseAdvocateRequestDto dto) {
		CaseAdvocateResponseDto response = caseAdvocateService.assignAdvocate(dto);
		return ResponseEntity.ok(ApiResponse.success("Advocate assigned successfully", response));
	}

	@PreAuthorize("hasAnyRole('ADMIN','ADVOCATE')")
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<CaseAdvocateResponseDto>> getById(@PathVariable Long id) {
		CaseAdvocateResponseDto response = caseAdvocateService.getCaseAdvocateById(id);
		return ResponseEntity.ok(ApiResponse.success("Fetched successfully", response));
	}

	@PreAuthorize("hasAnyRole('ADMIN','ADVOCATE')")
	@GetMapping("/case/{caseId}")
	public ResponseEntity<ApiResponse<List<CaseAdvocateResponseDto>>> getByCaseId(@PathVariable Long caseId) {
		List<CaseAdvocateResponseDto> list = caseAdvocateService.getAdvocatesByCaseId(caseId);
		return ResponseEntity.ok(ApiResponse.success("Fetched successfully", list));
	}

	@PreAuthorize("hasAnyRole('ADMIN','ADVOCATE')")
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<CaseAdvocateResponseDto>> update(@PathVariable Long id,
			@RequestBody CaseAdvocateRequestDto dto) {
		CaseAdvocateResponseDto response = caseAdvocateService.updateCaseAdvocateRole(id, dto);
		return ResponseEntity.ok(ApiResponse.success("Updated successfully", response));
	}

	@PreAuthorize("hasAnyRole('ADMIN','ADVOCATE')")
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> unassign(@PathVariable Long id) {
		caseAdvocateService.unassignAdvocate(id);
		return ResponseEntity.ok(ApiResponse.success("Advocate unassigned successfully", null));
	}
}
