package com.asifiqbalsekh.demo.CountryCurrencyAPI.controller;

import com.asifiqbalsekh.demo.CountryCurrencyAPI.dto.*;
import com.asifiqbalsekh.demo.CountryCurrencyAPI.model.User;
import com.asifiqbalsekh.demo.CountryCurrencyAPI.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/info")
    public UserResponse getCurrentLoggedInUser( Authentication authentication) {
        if (authentication == null) {
            throw new RuntimeException("User Not Logged In");
        }
        User userDetails= (User) authentication.getPrincipal();
        return new UserResponse(
                "Logged In User Details Fetch Successfully! By Google Cloud!",
                userDetails.getEmail(),
                userDetails.getFullName(),
                userDetails.getRoles()
        );

    }

    @PostMapping
    public UserResponse addUserToDb(@Valid @RequestBody UserRequest userRequest) {

        return userService.addUserDetails(userRequest);
    }

    @PatchMapping("/roles")
    public UserResponse updateUserRoles(@Valid @RequestBody RolesRequest data) {
        return userService.updateRoles(data);
    }

    @GetMapping
    public List<AllUserResponse> getAllUsers() {
        return userService.getAllRegisteredUser();
    }
    @DeleteMapping("/deactivate/{email}")
    public UserResponse deactivateUserByEmail(@PathVariable String email) {
        return userService.deactivateUser(email);

    }
    @DeleteMapping("/{email}")
    public UserResponse deleteUserByEmail(@PathVariable String email) {
        return userService.deleteUser(email);

    }
    @PostMapping("/login")
    public LoginResponse loginUser(@Valid @RequestBody LoginRequest data) {
        return userService.verify(data);
    }
}
