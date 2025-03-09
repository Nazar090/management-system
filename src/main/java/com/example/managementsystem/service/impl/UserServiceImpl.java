package com.example.managementsystem.service.impl;

import com.example.managementsystem.dto.user.UserRegistrationDto;
import com.example.managementsystem.dto.user.UserRegistrationRequestDto;
import com.example.managementsystem.dto.user.profile.UserDto;
import com.example.managementsystem.dto.user.profile.UserRequestDto;
import com.example.managementsystem.dto.user.profile.UserRoleRequestDto;
import com.example.managementsystem.exception.EntityNotFoundException;
import com.example.managementsystem.exception.RegistrationException;
import com.example.managementsystem.mapper.UserMapper;
import com.example.managementsystem.model.Role;
import com.example.managementsystem.model.User;
import com.example.managementsystem.repository.RoleRepository;
import com.example.managementsystem.repository.UserRepository;
import com.example.managementsystem.security.AuthenticationService;
import com.example.managementsystem.service.UserService;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementation of the {@link UserService} for handling user registration and related operations.
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AuthenticationService authService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    /**
     * Registers a new user by validating the request, encoding the password, assigning
     * the default role,
     * and saving the user in the repository.
     *
     * @param requestDto The user registration request data, including email, password,
     *                   and other details.
     * @return A {@link UserRegistrationDto} containing the registered user's details.
     * @throws RegistrationException if a user with the given email already exists.
     * @throws EntityNotFoundException if the default USER role is not found in the database.
     */
    @Override
    public UserRegistrationDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RegistrationException("User with this email already exists");
        }
        User user = userMapper.toUserEntityFromRegistration(requestDto);
        Role role = roleRepository.findByRole(Role.RoleName.USER)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find role by role name " + Role.RoleName.USER.name()));
        user.setRoles(Set.of(role));
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        userRepository.save(user);

        return userMapper.toRegistrationDto(user);
    }

    /**
     * Retrieve the user's information in the profile
     *
     * @param userId the ID of the user who wants to view their profile.
     * @return user'r profile data
     */
    @Override
    public UserDto getUserInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can't find user by user id: " + userId));
        return userMapper.toDto(user);
    }

    /**
     * Update user's information in the profile
     *
     * @param requestDto updated information
     * @return updated product
     */
    @Override
    public UserDto updateUserInfo(UserRequestDto requestDto) {
        User user = userRepository.findById(authService.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "User not found by id: " + authService.getUserId()));
        userMapper.updateUserInfo(requestDto, user);
        return userMapper.toDto(userRepository.save(user));
    }

    /**
     * Updates the role information of a specific user.
     *
     * @param id the ID of the user whose role information is being updated.
     * @param requestDto the role update request data.
     * @return UserDto containing the updated user role details.
     */
    @Override
    public UserDto updateUserRoleInfo(Long id, UserRoleRequestDto requestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User not found by id: " + authService.getUserId()));
        userMapper.updateUserInfo(requestDto, user);
        return userMapper.toDto(userRepository.save(user));
    }

    /**
     * Retrieves a list of all users in the system.
     *
     * @return a list of UserDto representing all users.
     */
    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }
}
