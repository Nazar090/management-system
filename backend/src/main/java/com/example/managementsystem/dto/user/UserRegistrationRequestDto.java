package com.example.managementsystem.dto.user;

import com.example.managementsystem.validation.PasswordMatches;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@PasswordMatches
@Accessors(chain = true)
public class UserRegistrationRequestDto {
    @NotBlank(message = "Email can't be empty")
    @Email
    private String email;
    @NotBlank(message = "Enter your nickname")
    @Pattern(
            regexp = "^[a-z][a-z0-9._]*$",
            message = "Nickname must start with a letter and can only contain letters, "
                    + "digits, '.', and '_'"
    )
    private String nickname;
    @NotBlank(message = "Password can't be empty")
    @Length(min = 8, max = 35)
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!\\-]).{8,35}$",
            message = "Password must contain at least one digit, one lowercase letter, "
                    + "one uppercase letter, and one special character (@#$%^&+=!-)"
    )
    private String password;
    @NotBlank(message = "You have to repeat your password")
    @Length(min = 8, max = 35)
    private String repeatPassword;
    private String name;
}
