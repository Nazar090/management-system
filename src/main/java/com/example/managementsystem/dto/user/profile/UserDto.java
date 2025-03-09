package com.example.managementsystem.dto.user.profile;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Data
@Accessors(chain = true)
public class UserDto {
    private Long id;
    private String nickname;
    private String name;
    private String email;
}
