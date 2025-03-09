package com.example.managementsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.example.managementsystem.service.impl.UserServiceImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private AuthenticationService authService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDto userDto;
    private UserRegistrationRequestDto registrationRequest;
    private UserRegistrationDto registrationDto;
    private UserRequestDto userRequestDto;
    private UserRoleRequestDto userRoleRequestDto;
    private Role userRole;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);

        userDto = new UserDto();

        registrationRequest = new UserRegistrationRequestDto();
        registrationRequest.setEmail("test@example.com");
        registrationRequest.setPassword("password");

        registrationRequest.setEmail("test@example.com");

        userRequestDto = new UserRequestDto();
        userRoleRequestDto = new UserRoleRequestDto();

        userRole = new Role();
        userRole.setRole(Role.RoleName.USER);
    }

    @Test
    void register_ShouldRegisterUser_WhenValidRequest() throws RegistrationException {
        when(userRepository.existsByEmail(registrationRequest.getEmail())).thenReturn(false);
        when(userMapper.toUserEntityFromRegistration(registrationRequest)).thenReturn(user);
        when(roleRepository.findByRole(Role.RoleName.USER)).thenReturn(Optional.of(userRole));
        when(passwordEncoder.encode(registrationRequest.getPassword()))
                .thenReturn("encodedPassword");
        when(userMapper.toRegistrationDto(user)).thenReturn(registrationDto);

        UserRegistrationDto result = userService.register(registrationRequest);

        assertEquals(registrationDto, result);

        verify(userRepository).save(user);
    }

    @Test
    void register_ShouldThrowRegistrationException_WhenUserAlreadyExists() {
        when(userRepository.existsByEmail(registrationRequest.getEmail())).thenReturn(true);

        assertThrows(RegistrationException.class, () -> userService.register(registrationRequest));
    }

    @Test
    void getUserInfo_ShouldReturnUserDto_WhenUserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(user)).thenReturn(userDto);

        UserDto result = userService.getUserInfo(1L);

        assertEquals(userDto, result);
    }

    @Test
    void updateUserInfo_ShouldUpdateUser_WhenUserExists() {
        when(authService.getUserId()).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(userRepository.save(user))).thenReturn(userDto);

        UserDto result = userService.updateUserInfo(userRequestDto);

        assertEquals(userDto, result);
    }

    @Test
    void updateUserInfo_ShouldThrowEntityNotFoundException_WhenUserDoesNotExist() {
        when(authService.getUserId()).thenReturn(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> userService.updateUserInfo(userRequestDto));
    }

    @Test
    void updateUserRoleInfo_ShouldUpdateRole_WhenUserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDto(userRepository.save(user))).thenReturn(userDto);

        UserDto result = userService.updateUserRoleInfo(1L, userRoleRequestDto);

        assertEquals(userDto, result);
    }

    @Test
    void getAll_ShouldReturnListOfUserDto() {
        List<User> users = List.of(user);
        List<UserDto> userDtos = List.of(userDto);

        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.toDto(user)).thenReturn(userDto);

        List<UserDto> result = userService.getAll();

        assertEquals(userDtos, result);
    }
}
