package com.lms.cases.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	private final JwtAuthFilter jwtAuthFilter;

	public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
		this.jwtAuthFilter = jwtAuthFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth.requestMatchers("/cases/public/**","/actuator/**","/cases/instance").permitAll()
						  // 🔹 Case Management
	                    .requestMatchers(HttpMethod.POST, "/cases/**").hasAnyRole("ADMIN", "ADVOCATE")
	                    .requestMatchers(HttpMethod.PUT, "/cases/**").hasAnyRole("ADMIN", "ADVOCATE")
	                    .requestMatchers(HttpMethod.DELETE, "/cases/**").hasRole("ADMIN")
	                    .requestMatchers(HttpMethod.GET, "/cases/**").hasAnyRole("ADMIN", "ADVOCATE", "CLIENT")

	                    // 🔹 Hearing Management
	                    .requestMatchers(HttpMethod.POST, "/hearings/**").hasAnyRole("ADMIN", "ADVOCATE")
	                    .requestMatchers(HttpMethod.PUT, "/hearings/**").hasAnyRole("ADMIN", "ADVOCATE")
	                    .requestMatchers(HttpMethod.DELETE, "/hearings/**").hasRole("ADMIN")
	                    .requestMatchers(HttpMethod.GET, "/hearings/**").hasAnyRole("ADMIN", "ADVOCATE", "CLIENT")

	                    // 🔹 Schedule Management
	                    .requestMatchers(HttpMethod.POST, "/schedules/**").hasAnyRole("ADMIN", "ADVOCATE")
	                    .requestMatchers(HttpMethod.PUT, "/schedules/**").hasAnyRole("ADMIN", "ADVOCATE")
	                    .requestMatchers(HttpMethod.DELETE, "/schedules/**").hasRole("ADMIN")
	                    .requestMatchers(HttpMethod.GET, "/schedules/**").hasAnyRole("ADMIN", "ADVOCATE", "CLIENT")

	                    // Any other
						.anyRequest().authenticated())
				.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class).build();
	}
}
