package com.alexcode.photoapp.api.users.PhotoAppApiUsers.model.dto;

import com.alexcode.photoapp.api.users.PhotoAppApiUsers.model.entity.UserEntity;
import com.alexcode.photoapp.api.users.PhotoAppApiUsers.model.request.CreateUserRequest;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDto implements Serializable {

    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String encryptedPassword;

    public static UserDto of(CreateUserRequest request) {
        return UserDto.builder()
                .userId(UUID.randomUUID().toString())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(request.getPassword())
                .encryptedPassword("test")
                .build();
    }

    public static UserDto of(UserEntity userEntity) {
        return UserDto.builder()
                .userId(userEntity.getUserId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .encryptedPassword(userEntity.getEncryptedPassword())
                .build();
    }
}
