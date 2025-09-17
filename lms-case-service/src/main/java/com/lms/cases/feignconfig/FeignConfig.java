package com.lms.cases.feignconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;

import feign.RequestInterceptor;

@Configuration
public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            var auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.getCredentials() instanceof String) {
                String token = (String) auth.getCredentials();
                template.header("Authorization", "Bearer " + token);
               // System.out.println("Forwarding token to Case-service: " + token); // debug
            } else {
                System.out.println("⚠️ No token found in SecurityContext");
            }
        };
    }
}
