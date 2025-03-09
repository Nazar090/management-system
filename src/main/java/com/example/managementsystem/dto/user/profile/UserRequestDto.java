package com.example.managementsystem.dto.user.profile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class UserRequestDto {
    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String name;

    @NotBlank(message = "Nickname must not be blank")
    @Size(max = 25, message = "Last name must not exceed 25 characters")
    private String nickname;

    @Email(message = "Email should be valid")
    @Size(min = 8, max = 35, message = "Email must be between 8 and 35 characters")
    private String email;
}
