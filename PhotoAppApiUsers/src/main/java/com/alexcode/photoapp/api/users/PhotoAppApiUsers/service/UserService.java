package com.alexcode.photoapp.api.users.PhotoAppApiUsers.service;

import com.alexcode.photoapp.api.users.PhotoAppApiUsers.model.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDto createUser(UserDto userDto);

    UserDto getUserDetailsByEmail(String email);

    UserDto getUserById(String userId);

}
