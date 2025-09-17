package com.lms.user.service;

import java.util.List;

import com.lms.user.dto.UserRegisterDto;
import com.lms.user.dto.UserResponseDto;
import com.lms.user.dto.UserUpdateDto;
import com.lms.user.feign.dto.CaseResponseDto;

public interface AdminService {
	public UserResponseDto createClient(UserRegisterDto dto);

	public UserResponseDto createAdvocate(UserRegisterDto dto);

	public List<UserResponseDto> listAllUsers();
	
	public UserResponseDto getUserById(Long userId);

	public UserResponseDto updateUser(Long userId, UserUpdateDto userDto);

	public void deleteUser(Long userId);
	public List<CaseResponseDto> getUserCases(Long userId);
}
