package com.alexcode.photoapp.api.users.PhotoAppApiUsers.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "users")
public class UserController {

    @GetMapping(path = "status/check")
    public String status() {
        return "Working";
    }
}
