package com.example.managementsystem.config;

import com.example.managementsystem.security.JwtUtil;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class JwtTestConfig {
    @Bean
    public JwtUtil jwtUtil() {
        // Return a stub or a JwtUtil instance that does not validate the secret
        // For instance, you can use a constructor that bypasses the key validation,
        // or simply mock it if your tests do not require actual JWT functionality.
        return new JwtUtil("12345678901234567890123456789012");
    }
}
