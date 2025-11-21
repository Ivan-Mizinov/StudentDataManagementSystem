package io.synergy.controller;

import io.synergy.dto.UserDto;
import io.synergy.dto.UserResponseDto;
import io.synergy.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/api/service/User/linkUserToStudent")
    public ResponseEntity<Void> linkUserToStudent(
            @RequestParam String username,
            @RequestParam Long studentId
    ) {
        userService.linkUserToStudent(username, studentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/service/Users")
    public List<UserResponseDto> getAllUsers() {
        return userService.findAll();
    }
}
