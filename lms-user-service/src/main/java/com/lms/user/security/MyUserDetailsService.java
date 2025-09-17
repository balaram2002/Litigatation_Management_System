package com.lms.user.security;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.lms.user.entity.Users;
import com.lms.user.repository.UserRepository;

@Component
public class MyUserDetailsService implements UserDetailsService {
	private final UserRepository userRepo;

	public MyUserDetailsService(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    Users user = userRepo.findByUsername(username)
	            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

	    List<GrantedAuthority> authorities = List.of(
	        new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
	    );

	    return new CustomUserDetails(user, authorities);
	}
}
