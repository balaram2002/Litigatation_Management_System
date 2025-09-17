package com.lms.user.dto;

import java.time.LocalDateTime;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {
	private Long userId;
	private String username;
	private String name;
	private String email;
	private String phone;
	private String role;
	private LocalDateTime createdAt;
}
