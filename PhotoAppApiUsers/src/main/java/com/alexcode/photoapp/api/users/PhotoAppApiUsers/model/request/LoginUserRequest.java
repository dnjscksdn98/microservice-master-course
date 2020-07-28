package com.alexcode.photoapp.api.users.PhotoAppApiUsers.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserRequest {

    private String email;
    private String password;
}
