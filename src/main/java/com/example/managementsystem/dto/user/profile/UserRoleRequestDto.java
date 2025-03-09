package com.example.managementsystem.dto.user.profile;

import com.example.managementsystem.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class UserRoleRequestDto {
    private String name;
    private String nickname;
    @Email
    @Size(min = 8, max = 35)
    private String email;
    private Role role;
}
