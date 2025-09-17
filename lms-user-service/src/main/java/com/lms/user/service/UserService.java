package com.lms.user.service;



import java.util.List;

import com.lms.user.dto.UserResponseDto;
import com.lms.user.entity.Users;
import com.lms.user.feign.dto.CaseResponseDto;

public interface UserService {
	Users saveUser(String username, String password);

	String verifyUser(Users user);

	Users updateUser(Users user);

	String deleteUser(String username);

	UserResponseDto getUserByUsername(String username);

	List<UserResponseDto> getAllUsers();
	
	
}
