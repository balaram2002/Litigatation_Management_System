package com.lms.cases.controller;

import com.lms.cases.dto.ApiResponse;
import com.lms.cases.dto.ScheduleRequestDto;
import com.lms.cases.dto.ScheduleResponseDto;
import com.lms.cases.security.JwtUtil;
import com.lms.cases.service.ScheduleService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

	private final ScheduleService scheduleService;
	private final JwtUtil jwtUtil;

	@PreAuthorize("hasAnyRole('ADMIN','ADVOCATE')")
	@PostMapping
	public ResponseEntity<ApiResponse<ScheduleResponseDto>> createSchedule(@RequestBody ScheduleRequestDto dto,
			@RequestHeader("Authorization") String authorizationHeader) {

		String token = authorizationHeader.replace("Bearer ", "");
		Long userId = jwtUtil.extractUserId(token);
		List<String> roles = jwtUtil.extractAuthorities(token);

		ScheduleResponseDto response = scheduleService.createSchedule(dto, userId, roles);
		return ResponseEntity.ok(ApiResponse.success("Schedule created successfully", response));
	}

	@PreAuthorize("hasAnyRole('ADMIN','ADVOCATE')")
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<ScheduleResponseDto>> updateSchedule(@PathVariable Long id,
			@RequestBody ScheduleRequestDto dto, @RequestHeader("Authorization") String authorizationHeader) {

		String token = authorizationHeader.replace("Bearer ", "");
		Long userId = jwtUtil.extractUserId(token);
		List<String> roles = jwtUtil.extractAuthorities(token);

		ScheduleResponseDto response = scheduleService.updateSchedule(id, dto, userId, roles);
		return ResponseEntity.ok(ApiResponse.success("Schedule updated successfully", response));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteSchedule(@PathVariable Long id,
			@RequestHeader("Authorization") String authorizationHeader) {

		String token = authorizationHeader.replace("Bearer ", "");
		Long userId = jwtUtil.extractUserId(token);
		List<String> roles = jwtUtil.extractAuthorities(token);

		scheduleService.deleteSchedule(id, userId, roles);
		return ResponseEntity.ok(ApiResponse.success("Schedule deleted successfully", null));
	}

	@PreAuthorize("hasAnyRole('ADMIN','ADVOCATE','CLIENT')")
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<ScheduleResponseDto>> getScheduleById(@PathVariable Long id,
			@RequestHeader("Authorization") String authorizationHeader) {

		String token = authorizationHeader.replace("Bearer ", "");
		Long userId = jwtUtil.extractUserId(token);
		List<String> roles = jwtUtil.extractAuthorities(token);

		ScheduleResponseDto response = scheduleService.getScheduleById(id, userId, roles);
		return ResponseEntity.ok(ApiResponse.success("Schedule fetched successfully", response));
	}

	@PreAuthorize("hasAnyRole('ADMIN','ADVOCATE','CLIENT')")
	@GetMapping("/case/{caseId}")
	public ResponseEntity<ApiResponse<List<ScheduleResponseDto>>> getSchedulesByCase(@PathVariable Long caseId,
			@RequestHeader("Authorization") String authorizationHeader) {

		String token = authorizationHeader.replace("Bearer ", "");
		Long userId = jwtUtil.extractUserId(token);
		List<String> roles = jwtUtil.extractAuthorities(token);

		List<ScheduleResponseDto> response = scheduleService.getSchedulesByCase(caseId, userId, roles);
		return ResponseEntity.ok(ApiResponse.success("Schedules fetched successfully", response));
	}
}
