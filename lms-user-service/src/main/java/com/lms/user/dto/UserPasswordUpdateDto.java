package com.lms.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserPasswordUpdateDto {

	@NotBlank(message = "Old password is required")
	private String oldPassword;

	@NotBlank(message = "New password is required")
	@Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
	private String newPassword;

}
