package com.lms.user.service;

import com.lms.user.dto.UserLoginDto;

public interface AuthService {
	String verifyUser(UserLoginDto user);
}
