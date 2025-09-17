package com.lms.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.user.dto.ApiResponse;
import com.lms.user.dto.UserLoginDto;
import com.lms.user.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		super();
		this.authService = authService;
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<?>> login(@RequestBody UserLoginDto user) {
		String token = authService.verifyUser(user);

		if (token.startsWith("User verification failed")) {
			return ResponseEntity.ok(ApiResponse.error("Invalid credentilas"));
		}
		return ResponseEntity.ok(ApiResponse.success("Login successfull", token));
	}
}
