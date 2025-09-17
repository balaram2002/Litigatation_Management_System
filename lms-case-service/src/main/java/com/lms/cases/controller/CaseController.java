package com.lms.cases.controller;

import com.lms.cases.dto.CaseRequestDto;

import com.lms.cases.dto.CaseResponseDto;
import com.lms.cases.feign.dto.DocumentResponseDto;
import com.lms.cases.service.CaseService;
import com.lms.cases.dto.ApiResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/cases")
@RequiredArgsConstructor
public class CaseController {
	
	@Value("${server.port}")
    private String port;

	private final CaseService caseService;

	/**
	 * Creates a new case. Accessible by ADMIN and ADVOCATE roles only.
	 *
	 * @param dto the case request DTO
	 * @return created case wrapped in ApiResponse
	 */
	@PreAuthorize("hasAnyRole('ADMIN','ADVOCATE')")
	@PostMapping
	public ResponseEntity<ApiResponse<CaseResponseDto>> createCase(@Valid @RequestBody CaseRequestDto dto) {
		CaseResponseDto createdCase = caseService.createCase(dto);
		return ResponseEntity.ok(ApiResponse.success("Case created successfully", createdCase));
	}

	/**
	 * Updates an existing case by ID. Accessible by ADMIN and ADVOCATE roles only.
	 *
	 * @param id  case ID
	 * @param dto case update data
	 * @return updated case wrapped in ApiResponse
	 */
	@PreAuthorize("hasAnyRole('ADMIN','ADVOCATE')")
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<CaseResponseDto>> updateCase(@PathVariable Long id,
			@Valid @RequestBody CaseRequestDto dto) {
		CaseResponseDto updatedCase = caseService.updateCase(id, dto);
		return ResponseEntity.ok(ApiResponse.success("Case updated successfully", updatedCase));
	}

	/**
	 * Update the status of existing case by ID. Accessible by ADMIN and ADVOCATE
	 * roles only
	 * 
	 * @param caseId     case ID
	 * @param caseStatus case status
	 * @return case details wrapped in ApiResponse
	 */
	@PreAuthorize("hasAnyRole('ADMIN','ADVOCATE')")
	@PatchMapping("/{caseId}/{caseStatus}")
	public ResponseEntity<ApiResponse<CaseResponseDto>> changeCaseStatus(@PathVariable Long caseId,
			@PathVariable String caseStatus) {
		CaseResponseDto dto = caseService.changeCaseStatus(caseId, caseStatus);
		return ResponseEntity.ok(ApiResponse.success("Case status updated", dto));
	}

	/**
	 * Retrieves a case by its ID. Accessible by ADMIN, ADVOCATE, and CLIENT roles.
	 *
	 * @param id case ID
	 * @return case details wrapped in ApiResponse
	 */
	@PreAuthorize("hasAnyRole('ADMIN','ADVOCATE','CLIENT')")
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<CaseResponseDto>> getCaseById(@PathVariable Long id) {
		CaseResponseDto caseDto = caseService.getCaseById(id);
		return ResponseEntity.ok(ApiResponse.success("Case fetched successfully", caseDto));
	}

	/**
	 * Retrieves all cases optionally filtered by status. Accessible by ADMIN,
	 * ADVOCATE, and CLIENT roles.
	 *
	 * @param status optional filter for case status
	 * @return list of cases wrapped in ApiResponse
	 */
	@PreAuthorize("hasAnyRole('ADMIN','ADVOCATE','CLIENT')")
	@GetMapping
	public ResponseEntity<ApiResponse<List<CaseResponseDto>>> getAllCases(
			@RequestParam(required = false) String status) {
		List<CaseResponseDto> cases = caseService.getAllCases(status);
		return ResponseEntity.ok(ApiResponse.success("Cases fetched successfully", cases));
	}

	/**
	 * Deletes a case by ID (soft delete). Accessible by ADMIN role only.
	 *
	 * @param caseId ID of the case to delete
	 * @param userId ID of the user performing the deletion
	 * @return empty ApiResponse
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{caseId}/{userId}")
	public ResponseEntity<ApiResponse<Void>> deleteCase(@PathVariable Long caseId, @PathVariable Long userId) {
		caseService.deleteCase(caseId, userId);
		return ResponseEntity.ok(ApiResponse.success("Case deleted successfully", null));
	}

	// Get cases by userId (client or advocate)
	@PreAuthorize("hasAnyRole('ADMIN','ADVOCATE','CLIENT')")
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<CaseResponseDto>> getCasesByUserId(@PathVariable Long userId) {
		List<CaseResponseDto> cases = caseService.getCasesByUserId(userId);
		return ResponseEntity.ok(cases);
	}
	
	@PreAuthorize("hasAnyRole('ADMIN','ADVOCATE','CLIENT')")
	@GetMapping("/{caseId}/documents")
	public ResponseEntity<List<DocumentResponseDto>> getDocumentByCaseId(@PathVariable Long caseId) {
		return ResponseEntity.ok(caseService.getDocumentsByCaseId(caseId));
	}

	 @GetMapping("/instance")
	    public String getInstancePort() {
	        return "Response from User-Service running on port: " + port;
	    }

}
