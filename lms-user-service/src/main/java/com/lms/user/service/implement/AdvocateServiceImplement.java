package com.lms.user.service.implement;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lms.user.dto.UserPasswordUpdateDto;
import com.lms.user.dto.UserResponseDto;
import com.lms.user.entity.Users;
import com.lms.user.enums.Role;
import com.lms.user.feign.CaseClient;
import com.lms.user.feign.dto.CaseResponseDto;
import com.lms.user.mapper.UserMapper;
import com.lms.user.repository.UserRepository;
import com.lms.user.service.AdvocateService;

@Service
public class AdvocateServiceImplement implements AdvocateService {
	
	//dependencies
	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	private final CaseClient caseClient;

	// constructor DI
	public AdvocateServiceImplement(UserRepository userRepo, PasswordEncoder passwordEncoder, CaseClient caseClient) {
		super();
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
		this.caseClient = caseClient;
	}

	// get profile
	@Override
    public UserResponseDto getProfile(String username) {
        Users user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        if (user.getRole() != Role.ADVOCATE) {
            throw new IllegalArgumentException("User is not an advocate");
        }
        return UserMapper.toDTO(user);
    }
	
	

	// update password 
	@Override
	public void updatePassword(long userId, UserPasswordUpdateDto dto) {
		Users user = userRepo.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));

		// validate old password
		if (dto.getOldPassword() != null && !passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
			throw new IllegalArgumentException("Old password does not match");
		}

		  if (user.getRole() != Role.ADVOCATE) {
	            throw new IllegalArgumentException("User is not an advocate");
	        }
		// Encode new password
		user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
		userRepo.save(user);
	}

	@Override
	public List<CaseResponseDto> getUserCases(Long userId) {
		return caseClient.getCasesByUserId(userId); // Feign call
	}
}