package com.alexcode.photoapp.api.users.PhotoAppApiUsers.controller;

import com.alexcode.photoapp.api.users.PhotoAppApiUsers.model.dto.UserDto;
import com.alexcode.photoapp.api.users.PhotoAppApiUsers.model.request.CreateUserRequest;
import com.alexcode.photoapp.api.users.PhotoAppApiUsers.model.response.UserResponse;
import com.alexcode.photoapp.api.users.PhotoAppApiUsers.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
        return "Working on port " + env.getProperty("local.server.port") + ", with secret key : " + env.getProperty("jwt.secretKey");
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserDto userDto = UserDto.of(request);
        UserResponse response = UserResponse.of(userService.createUser(userDto));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
