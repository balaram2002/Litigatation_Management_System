package com.lms.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.user.dto.ApiResponse;
import com.lms.user.dto.UserRegisterDto;
import com.lms.user.dto.UserResponseDto;
import com.lms.user.dto.UserUpdateDto;
import com.lms.user.feign.dto.CaseResponseDto;
import com.lms.user.service.AdminService;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

	// Dependencies
	private final AdminService adminService;
	@Value("${server.port}")
    private String port;

	// Constructor DI
	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}

	// create advocate by admin
	@PostMapping("/advocate")
	public ResponseEntity<ApiResponse<?>> createAdvocate(@RequestBody UserRegisterDto dto) {
		return ResponseEntity.ok(ApiResponse.success("Advocate created", adminService.createAdvocate(dto)));
	}

	// create client by admin
	@PostMapping("/client")
	public ResponseEntity<ApiResponse<?>> createClient(@RequestBody UserRegisterDto dto) {
		return ResponseEntity.ok(ApiResponse.success("Client created", adminService.createClient(dto)));
	}
	

    // Get user by ID
    @GetMapping("users/{userId}")
    public ResponseEntity<ApiResponse<UserResponseDto>> getUserById(@PathVariable long userId) {
        UserResponseDto user = adminService.getUserById(userId);
        return ResponseEntity.ok(ApiResponse.success("User retrieved successfully", user));
    }

	// get all users 
	@GetMapping("/users")
	public ResponseEntity<ApiResponse<List<?>>> listAllUsers() {
		return ResponseEntity.ok(ApiResponse.success("List of all users", adminService.listAllUsers()));
	}
	
	// deleteUser(Client/Advocate)
    @DeleteMapping("{userId}")
    public ResponseEntity<ApiResponse<?>> deleteUser(@PathVariable long userId) {
        adminService.deleteUser(userId);
        return ResponseEntity.ok(ApiResponse.success("User deleted successfully", null));
    }

    
    //update user(Client/Advocate) by admin
    @PatchMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<?>> updateUser(
            @PathVariable long userId,
            @RequestBody UserUpdateDto dto) {
        return ResponseEntity.ok(
            ApiResponse.success("User updated successfully", adminService.updateUser(userId, dto))
        );
    }
    
    @GetMapping("/{userId}/cases")
    public ResponseEntity<List<CaseResponseDto>> getUserCases(@PathVariable Long userId) {
        return ResponseEntity.ok(adminService.getUserCases(userId));
    }
    
    @GetMapping("/instance")
    public String getInstancePort() {
        return "Response from User-Service running on port: " + port;
    }
}
