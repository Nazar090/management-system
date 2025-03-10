package com.example.managementsystem.dto.user;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class UserRegistrationDto {
    private Long id;
    private String email;
    private String nickname;
}
