package com.alexcode.photoapp.api.users.PhotoAppApiUsers.model.response;

import com.alexcode.photoapp.api.users.PhotoAppApiUsers.model.dto.UserDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserDetailResponse {

    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private List<AlbumDetailResponse> albums;

    public static UserDetailResponse of(UserDto userDto) {
        return UserDetailResponse.builder()
                .userId(userDto.getUserId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .albums(userDto.getAlbums())
                .build();
    }
}
