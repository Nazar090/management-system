package com.example.managementsystem.service;

import com.example.managementsystem.dto.user.UserRegistrationDto;
import com.example.managementsystem.dto.user.UserRegistrationRequestDto;
import com.example.managementsystem.dto.user.profile.UserDto;
import com.example.managementsystem.dto.user.profile.UserRequestDto;
import com.example.managementsystem.dto.user.profile.UserRoleRequestDto;
import com.example.managementsystem.exception.RegistrationException;
import java.util.List;

public interface UserService {
    UserRegistrationDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException;

    UserDto getUserInfo(Long userId);

    UserDto updateUserInfo(UserRequestDto requestDto);

    UserDto updateUserRoleInfo(Long id, UserRoleRequestDto requestDto);

    List<UserDto> getAll();
}
