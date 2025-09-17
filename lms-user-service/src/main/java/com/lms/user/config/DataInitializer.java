package com.lms.user.config;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.lms.user.entity.Users;
import com.lms.user.enums.Role;
import com.lms.user.repository.UserRepository;

@Configuration
public class DataInitializer {
	
// admin creation to the system if system has no admin
	@Bean
    CommandLineRunner createAdmin(UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {
            if(userRepository.count() == 0) {
                Users admin = Users.builder()
                        .username("admin")
                        .name("Admin User")
                        .email("admin@example.com")
                        .phone("9876543210")
                        .password(encoder.encode("admin123"))
                        .role(Role.ADMIN)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .isDeleted(false)
                        .build();
				userRepository.save(admin);
				System.out.println("Admin user created!");
			}
		};
	}
}
