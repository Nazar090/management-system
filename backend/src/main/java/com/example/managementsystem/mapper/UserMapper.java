package com.example.managementsystem.mapper;

import com.example.managementsystem.config.MapperConfig;
import com.example.managementsystem.dto.user.UserRegistrationDto;
import com.example.managementsystem.dto.user.UserRegistrationRequestDto;
import com.example.managementsystem.dto.user.profile.UserDto;
import com.example.managementsystem.dto.user.profile.UserRequestDto;
import com.example.managementsystem.dto.user.profile.UserRoleRequestDto;
import com.example.managementsystem.model.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    UserRegistrationDto toRegistrationDto(User user);

    UserDto toDto(User user);

    User toUserEntityFromRegistration(UserRegistrationRequestDto requestDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserInfo(UserRequestDto requestDto, @MappingTarget User user);

    void updateUserInfo(UserRoleRequestDto requestDto, @MappingTarget User user);
}
