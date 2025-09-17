package com.lms.user.mapper;


import com.lms.user.dto.UserRegisterDto;
import com.lms.user.dto.UserResponseDto;
import com.lms.user.entity.Users;
import com.lms.user.enums.Role;

public class UserMapper {

		 // DTO -> Entity (UserRegister)
	    public static Users toEntity(UserRegisterDto dto, String encodedPassword) {
	        return Users.builder()
	                .name(dto.getName())
	                .username(dto.getUsername())
	                .email(dto.getEmail())
	                .phone(dto.getPhone())
	                .role(Role.valueOf(dto.getRole().toUpperCase()))
	                .password(encodedPassword)
	                .build();
	    }
	
	    // Entity -> DTO (UserResponse)
	    public static UserResponseDto toDTO(Users user) {
	        return new UserResponseDto(
	                user.getUserId(),
	                user.getUsername(),
	                user.getName(),
	                user.getEmail(),
	                user.getPhone(),
	                user.getRole().name(),
	                user.getCreatedAt()
	        );
	    }

	// Entity -> Response DTO
	public static UserResponseDto toResponseDto(Users user) {
		return UserResponseDto.builder()
				.userId(user.getUserId())
				.username(user.getUsername())
				.name(user.getName())
				.email(user.getEmail())
				.phone(user.getPhone())
				.role(user.getRole().name())
				.createdAt(user.getCreatedAt() != null ? user
						.getCreatedAt() : null).build();
	}

}
