package com.lms.user.service;

import java.util.List;

import com.lms.user.dto.UserPasswordUpdateDto;
import com.lms.user.dto.UserResponseDto;
import com.lms.user.feign.dto.CaseResponseDto;

public interface AdvocateService {
	UserResponseDto getProfile(String username);
	void updatePassword(long userId, UserPasswordUpdateDto dto);
	public List<CaseResponseDto> getUserCases(Long userId);
}
