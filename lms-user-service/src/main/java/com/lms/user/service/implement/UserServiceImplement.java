package com.lms.user.service.implement;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lms.user.dto.UserResponseDto;
import com.lms.user.entity.Users;
import com.lms.user.exception.UserAlreadyExistException;
import com.lms.user.feign.CaseClient;
import com.lms.user.feign.dto.CaseResponseDto;
import com.lms.user.mapper.UserMapper;
import com.lms.user.repository.UserRepository;
import com.lms.user.security.JwtUtil;
import com.lms.user.service.UserService;

@Service
public class UserServiceImplement implements UserService {

	private PasswordEncoder passwordEncoder;
	private UserRepository userRepo;
	private AuthenticationManager authenticationManager;
	private JwtUtil jwtUtil;
	private final CaseClient caseClient;
	

	public UserServiceImplement(PasswordEncoder passwordEncoder, UserRepository userRepo,
			AuthenticationManager authenticationManager, JwtUtil jwtUtil, CaseClient caseClient) {
		super();
		this.passwordEncoder = passwordEncoder;
		this.userRepo = userRepo;
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
		this.caseClient = caseClient;
	}

	/*
	 * save user method throws exception if the username is not available encode the
	 * password before saving the entity user into database
	 */
	@Override
	public Users saveUser(String username, String password) {

		Optional<Users> existingUser = userRepo.findByUsername(username);
		if (!existingUser.isEmpty()) {
			throw new UserAlreadyExistException("Username :" + username + "already exists");
		}
		Users user = new Users();
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		return userRepo.save(user);

	}

	// verify user for login

	@Override
	public String verifyUser(Users user) {
	    Authentication authentication = authenticationManager
	            .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

	    if (authentication.isAuthenticated()) {
	        org.springframework.security.core.userdetails.User principal =
	                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

	        String role = principal.getAuthorities().iterator().next().getAuthority(); 
	        // This gives "ROLE_ADMIN" or "ROLE_USER" — adjust if you only want raw role names.

	        return jwtUtil.generateAccessToken(user.getUserId(), user.getUsername(), role.replace("ROLE_", "")); // ✅ include ID
	    }

	    return "User verification failed";
	}


	@Override
	public Users updateUser(Users user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserResponseDto getUserByUsername(String username) {
		Users user = userRepo.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

		return UserMapper.toDTO(user);
	}

	
	// get all users(CLIENT/ADVOCATE/ADMIN)
	@Override
	public List<UserResponseDto> getAllUsers() {
		return userRepo.findAll().stream().map(UserMapper::toDTO).collect(Collectors.toList());
	}

//	@Override
//	public List<CaseResponseDto> getUserCases(Long userId, String authHeader) {
//		return caseClient.getCasesByUserId(userId, authHeader); // Feign call
//	}

}
