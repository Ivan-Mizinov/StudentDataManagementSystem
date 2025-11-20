package io.synergy.controller;

import io.synergy.dto.UserDto;
import io.synergy.dto.UserResponseDto;
import io.synergy.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/api/service/User")
    public UserResponseDto createUser(@RequestBody UserDto user) {
        return userService.saveUser(user);
    }

    @GetMapping("/api/service/Users")
    public List<UserResponseDto> getAllUsers() {
        return userService.findAll();
    }
}
