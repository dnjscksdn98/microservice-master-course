package com.alexcode.photoapp.api.users.PhotoAppApiUsers.controller;

import com.alexcode.photoapp.api.users.PhotoAppApiUsers.model.dto.UserDto;
import com.alexcode.photoapp.api.users.PhotoAppApiUsers.model.request.CreateUserRequest;
import com.alexcode.photoapp.api.users.PhotoAppApiUsers.model.response.CreateUserResponse;
import com.alexcode.photoapp.api.users.PhotoAppApiUsers.model.response.UserDetailResponse;
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
    public ResponseEntity<CreateUserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserDto userDto = UserDto.of(request);
        CreateUserResponse response = CreateUserResponse.of(userService.createUser(userDto));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(path = "{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDetailResponse> getUser(@PathVariable("userId") String userId) {
        UserDto userDto = userService.getUserById(userId);
        UserDetailResponse response = UserDetailResponse.of(userDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
