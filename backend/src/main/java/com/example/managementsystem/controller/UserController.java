package com.example.managementsystem.controller;

import com.example.managementsystem.dto.user.profile.UserDto;
import com.example.managementsystem.dto.user.profile.UserRequestDto;
import com.example.managementsystem.security.AuthenticationService;
import com.example.managementsystem.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsible for managing user-related endpoints, such as
 * retrieving and updating profile information and accessing user-specific
 * orders.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user/profile")
@Tag(name = "User profile", description = "Operations related to users profile")
public class UserController {
    private final AuthenticationService authService;
    private final UserService userService;

    /**
     * Retrieves profile information of the currently authenticated user.
     *
     * @return UserDto containing the profile details of the authenticated user.
     */
    @GetMapping
    @Operation(summary = "Retrieve User Profile Information",
            description = "Retrieve the details of a specific item within an order"
                    + " using the order ID and item ID.")
    public UserDto getUserInfo() {
        return userService.getUserInfo(authService.getUserId());
    }

    /**
     * Updates profile information for the currently authenticated user.
     *
     * @param requestDto contains the updated profile information.
     * @return UserDto containing the updated profile details.
     */
    @PostMapping("/update")
    @Operation(summary = "Update User Profile Information",
            description = "Update the profile information of the authenticated user.")
    public UserDto updateUserInfo(@RequestBody @Valid UserRequestDto requestDto) {
        return userService.updateUserInfo(requestDto);
    }
}
