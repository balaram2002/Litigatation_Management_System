package com.lms.cases.controller;

import com.lms.cases.dto.ApiResponse;
import com.lms.cases.dto.HearingRequestDto;
import com.lms.cases.dto.HearingResponseDto;
import com.lms.cases.dto.HearingUpdateDto;
import com.lms.cases.security.JwtUtil;
import com.lms.cases.service.HearingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hearings")
@RequiredArgsConstructor
public class HearingController {

    private final HearingService hearingService;
    private final JwtUtil jwtUtil;

    @PreAuthorize("hasAnyRole('ADMIN','ADVOCATE')")
    @PostMapping
    public ResponseEntity<ApiResponse<HearingResponseDto>> createHearing(@Valid @RequestBody HearingRequestDto dto) {
        HearingResponseDto createdHearing = hearingService.createHearing(dto);
        return ResponseEntity.ok(ApiResponse.success("Hearing created successfully", createdHearing));
    }

    @PreAuthorize("hasAnyRole('ADMIN','ADVOCATE','CLIENT')")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HearingResponseDto>> getHearingById(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authorizationHeader) {
        try {
            String token = authorizationHeader.replace("Bearer ", "");
            Long userId = jwtUtil.extractUserId(token);
            List<String> roles = jwtUtil.extractAuthorities(token);
            HearingResponseDto hearing = hearingService.getHearingByIdForUser(id, userId, roles);
            return ResponseEntity.ok(ApiResponse.success("Hearing fetched successfully", hearing));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Invalid userId claim in token: " + ex.getMessage()));
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','ADVOCATE','CLIENT')")
    @GetMapping("/case/{caseId}")
    public ResponseEntity<ApiResponse<List<HearingResponseDto>>> getHearingsByCaseId(
            @PathVariable Long caseId,
            @RequestHeader("Authorization") String authorizationHeader) {
        try {
            String token = authorizationHeader.replace("Bearer ", "");
            Long userId = jwtUtil.extractUserId(token);
            List<String> roles = jwtUtil.extractAuthorities(token);
            List<HearingResponseDto> hearings = hearingService.getHearingsByCaseIdForUser(caseId, userId, roles);
            return ResponseEntity.ok(ApiResponse.success("Hearings fetched successfully", hearings));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Invalid userId claim in token: " + ex.getMessage()));
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN','ADVOCATE')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<HearingResponseDto>> updateHearing(
            @PathVariable Long id,
            @Valid @RequestBody HearingUpdateDto dto) {

        HearingResponseDto updatedHearing = hearingService.updateHearing(id, dto);
        return ResponseEntity.ok(ApiResponse.success("Hearing updated successfully", updatedHearing));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteHearing(
            @PathVariable Long id,
            @RequestHeader("Authorization") String authorizationHeader) {
        try {
            String token = authorizationHeader.replace("Bearer ", "");
            Long deletedBy = jwtUtil.extractUserId(token);
            hearingService.deleteHearing(id, deletedBy);
            return ResponseEntity.ok(ApiResponse.success("Hearing deleted successfully", null));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Invalid userId claim in token: " + ex.getMessage()));
        }
    }
}