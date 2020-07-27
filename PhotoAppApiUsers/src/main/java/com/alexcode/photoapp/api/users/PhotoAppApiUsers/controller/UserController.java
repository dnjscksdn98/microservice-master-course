package com.alexcode.photoapp.api.users.PhotoAppApiUsers.controller;

import com.alexcode.photoapp.api.users.PhotoAppApiUsers.model.dto.UserDto;
import com.alexcode.photoapp.api.users.PhotoAppApiUsers.model.request.CreateUserRequest;
import com.alexcode.photoapp.api.users.PhotoAppApiUsers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "users")
public class UserController {

    @Autowired
    private Environment env;

    @Autowired
    private UserService userService;

    @GetMapping(path = "status/check")
    public String status() {
        return "Working on port " + env.getProperty("local.server.port");
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserDto userDto = UserDto.of(request);
        UserDto response = userService.createUser(userDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
