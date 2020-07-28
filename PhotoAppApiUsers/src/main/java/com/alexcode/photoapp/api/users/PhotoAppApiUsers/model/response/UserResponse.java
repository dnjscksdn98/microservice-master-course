package com.alexcode.photoapp.api.users.PhotoAppApiUsers.model.response;

import com.alexcode.photoapp.api.users.PhotoAppApiUsers.model.dto.UserDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

    private String userId;
    private String firstName;
    private String lastName;
    private String email;

    public static UserResponse of(UserDto userDto) {
        return UserResponse.builder()
                .userId(userDto.getUserId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .build();
    }
}
