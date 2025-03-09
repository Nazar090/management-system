package com.example.managementsystem.security;

import com.example.managementsystem.model.User;
import com.example.managementsystem.repository.UserRepository;
import java.util.Collections;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private static final String PLACEHOLDER_PASSWORD = "OAUTH2_LOGIN";

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // Delegate to the default service to fetch user details from the provider
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oauthUser = delegate.loadUser(userRequest);

        Map<String, Object> attributes = oauthUser.getAttributes();
        String email = (String) attributes.get("email");

        // Retrieve or create a new user in your database
        User user = userRepository.findByEmail(email).orElse(new User());
        user.setEmail(email);
        user.setPassword(PLACEHOLDER_PASSWORD);
        String emailPrefix = email.substring(0, email.indexOf('@'));
        user.setNickname(emailPrefix);

        // Optionally, update other fields from the provider attributes
        // e.g., firstName, lastName, etc.
        userRepository.save(user);

        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        // Return a DefaultOAuth2User with proper authorities
        return new DefaultOAuth2User(Collections.singleton(
                new SimpleGrantedAuthority("ROLE_USER")), attributes, userNameAttributeName);
    }
}
