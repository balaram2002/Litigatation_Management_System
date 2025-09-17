package com.lms.user.service.implement;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lms.user.dto.UserRegisterDto;
import com.lms.user.dto.UserResponseDto;
import com.lms.user.dto.UserUpdateDto;
import com.lms.user.entity.Users;
import com.lms.user.enums.Role;
import com.lms.user.exception.UserAlreadyExistException;
import com.lms.user.feign.CaseClient;
import com.lms.user.feign.dto.CaseResponseDto;
import com.lms.user.mapper.UserMapper;
import com.lms.user.repository.UserRepository;
import com.lms.user.service.AdminService;

@Service
public class AdminServiceImplement implements AdminService {

	// Dependencies
	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	private final CaseClient caseClient;

	// Constructor DI
	public AdminServiceImplement(UserRepository userRepo, PasswordEncoder passwordEncoder, CaseClient caseClient) {
		super();
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
		this.caseClient = caseClient;
	}

	// create client account
	public UserResponseDto createClient(UserRegisterDto dto) {

		if (!dto.getRole().equalsIgnoreCase("CLIENT")) {
			throw new IllegalArgumentException("Only CLIENT role allowed here");
		}
		if (userRepo.findByUsername(dto.getUsername()).isPresent()) {
			throw new UserAlreadyExistException("User already exists with username: " + dto.getUsername());
		}

		Users newUser = UserMapper.toEntity(dto, passwordEncoder.encode(dto.getPassword()));
		Users savedUser = userRepo.save(newUser);

		return UserMapper.toDTO(savedUser);
	}

	// create advocate account
	@Override
	public UserResponseDto createAdvocate(UserRegisterDto dto) {
		if (!dto.getRole().equalsIgnoreCase("ADVOCATE")) {
			throw new IllegalArgumentException("Only ADVOCATE role allowed here");
		}

		if (userRepo.findByUsername(dto.getUsername()).isPresent()) {
			throw new UserAlreadyExistException("Username already taken: " + dto.getUsername());
		}

		String encodedPassword = passwordEncoder.encode(dto.getPassword());
		Users advocate = UserMapper.toEntity(dto, encodedPassword);
		Users saved = userRepo.save(advocate);
		return UserMapper.toDTO(saved);
	}

	// get all users
	@Override
	public List<UserResponseDto> listAllUsers() {
		return userRepo.findAll().stream().map(UserMapper::toDTO).collect(Collectors.toList());
	}

	// update user by admin
	@Override
	public UserResponseDto updateUser(Long userId, UserUpdateDto userDto) {
		Users user = userRepo.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));

		// Update only fields that are provided
		if (userDto.getName() != null && !userDto.getName().isBlank()) {
			user.setName(userDto.getName());
		}
		if (userDto.getEmail() != null && !userDto.getEmail().isBlank()) {
			user.setEmail(userDto.getEmail());
		}
		if (userDto.getPhone() != null && !userDto.getPhone().isBlank()) {
			user.setPhone(userDto.getPhone());
		}
		if (userDto.getRole() != null) {
			user.setRole(userDto.getRole());
		}

		Users updatedUser = userRepo.save(user);
		return UserMapper.toDTO(updatedUser);
	}

	// delete user by admin
	@Override
	public void deleteUser(Long userId) {
		Users user = userRepo.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));

		userRepo.delete(user);
	}

	// get user by id
	@Override
	public UserResponseDto getUserById(Long userId) {
		Users user = userRepo.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));

		return UserMapper.toResponseDto(user);
	}

	@Override
	public List<CaseResponseDto> getUserCases(Long userId) {
		return caseClient.getCasesByUserId(userId); // Feign call
	}

}
