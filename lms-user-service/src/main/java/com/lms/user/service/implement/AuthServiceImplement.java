package com.lms.user.service.implement;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lms.user.dto.UserLoginDto;
import com.lms.user.entity.Users;
import com.lms.user.repository.UserRepository;
import com.lms.user.security.JwtUtil;
import com.lms.user.service.AuthService;

@Service
public class AuthServiceImplement implements AuthService {

	private AuthenticationManager authenticationManager;
	private UserRepository userRepo;
	private JwtUtil jwtUtil;

	public AuthServiceImplement(AuthenticationManager authenticationManager, UserRepository userRepo, JwtUtil jwtUtil) {
		super();
		this.authenticationManager = authenticationManager;
		this.userRepo = userRepo;
		this.jwtUtil = jwtUtil;
	}

	@Override
	public String verifyUser(UserLoginDto user) {
	    Authentication authentication = authenticationManager
	            .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

	    if (authentication.isAuthenticated()) {
	        Users dbUser = userRepo.findByUsername(user.getUsername())
	                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
	        String role = dbUser.getRole().name(); // e.g., ADMIN, ADVOCATE
	        return jwtUtil.generateAccessToken(dbUser.getUserId(), dbUser.getUsername(), role); // ✅ pass userId too
	    }

	    return "User verification failed";
	}


}
