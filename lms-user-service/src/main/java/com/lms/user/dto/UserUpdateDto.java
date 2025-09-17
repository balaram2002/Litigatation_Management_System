package com.lms.user.dto;

import com.lms.user.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDto {

	@Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
	private String username;

	@Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
	private String name;

	@Email(message = "Email must be valid")
	private String email;

	@Pattern(regexp = "^[0-9]{10,15}$", message = "Phone number must contain only digits (10–15 characters)")
	private String phone;

	@Pattern(regexp = "^(ADMIN|ADVOCATE|CLIENT)$", message = "Role must be one of: ADMIN, ADVOCATE, CLIENT")
	private Role role;

}
