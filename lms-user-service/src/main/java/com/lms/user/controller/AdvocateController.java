package com.lms.user.controller;

import java.util.List;


import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.user.dto.ApiResponse;
import com.lms.user.dto.UserPasswordUpdateDto;
import com.lms.user.feign.dto.CaseResponseDto;
import com.lms.user.service.AdvocateService;

@RestController
@RequestMapping("/advocate")
@PreAuthorize("hasRole('ADVOCATE')")
public class AdvocateController {

	// Dependencies
	private final AdvocateService advocateService;

	// Constructor DI
	public AdvocateController(AdvocateService advocateService) {
		this.advocateService = advocateService;
	}

	// get profile
	@GetMapping
	public ResponseEntity<ApiResponse<?>> getProfile(Authentication auth) {
		String username = auth.getName(); // extracted from JWT
		return ResponseEntity.ok(ApiResponse.success("Advocate profile", advocateService.getProfile(username)));
	}
	
	// User updates their own password
    @PatchMapping("/{userId}/change-password")
    public ResponseEntity<ApiResponse<?>> updatePassword(
            @PathVariable long userId,
            @RequestBody UserPasswordUpdateDto dto) {
        advocateService.updatePassword(userId, dto);
        return ResponseEntity.ok(ApiResponse.success("Password updated successfully", null));
    }
    
    @GetMapping("/{userId}/cases")
    public ResponseEntity<List<CaseResponseDto>> getUserCases(@PathVariable Long userId) {
        return ResponseEntity.ok(advocateService.getUserCases(userId));
    }
}
