package com.example.managementsystem.config;

import com.example.managementsystem.security.CustomOAuth2UserService;
import com.example.managementsystem.security.CustomUserDetailsService;
import com.example.managementsystem.security.JwtAuthenticationFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration file for setting up Spring Security with JWT authentication.
 * <p>
 *     This configuration sets up security filters for the application, including:
 *     – JWT authentication filter for token-based authentication
 *     – Stateless session management
 * </p>
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     *
     * <p>
     *     This method defines the security behavior for different endpoints:
     *     – It disables
     * </p>
     */
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            CustomOAuth2UserService customOAuth2UserService) throws Exception {
        return http
            .cors(AbstractHttpConfigurer::disable)
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(
                    auth -> auth
                            .requestMatchers(
                                    "/api/auth/**",
                                    "/swagger-ui/**",
                                    "/v3/api-docs/**",
                                    "/api/products/**",
                                    "/error",
                                    "/oauth2/**",
                                    "/api/home")
                            .permitAll()
                            .anyRequest()
                            .authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
                    // The authorization endpoint is where the client will start the OAuth2 flow:
                    .authorizationEndpoint(authorization ->
                            authorization
                            .baseUri("/oauth2/authorize")
                    )
                    // The redirection endpoint is where the provider sends users back:
                    .redirectionEndpoint(redirection -> redirection
                            .baseUri("/oauth2/callback/*")
                    )
                    // The userInfoEndpoint is used to fetch the user's details from the provider:
                    .userInfoEndpoint(userInfo -> userInfo
                            .userService(customOAuth2UserService)
                    )
                    // On success, you can do a custom action, or redirect, or return a token:
                    .successHandler((request, response,
                                     authentication) -> {
                        // E.g., redirect somewhere, or write a token to the response, etc.
                        response.sendRedirect("/api/home");
                    })
            )
            .logout(logout -> logout
                        .logoutUrl("/api/auth/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessHandler((request, response,
                                               authentication) -> {
                            response.setStatus(HttpServletResponse.SC_OK);
                            response.getWriter().write("{\"message\": \"Logout successful\"}");
                        })
                )
            .httpBasic(Customizer.withDefaults())
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .userDetailsService(customUserDetailService)
            .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
