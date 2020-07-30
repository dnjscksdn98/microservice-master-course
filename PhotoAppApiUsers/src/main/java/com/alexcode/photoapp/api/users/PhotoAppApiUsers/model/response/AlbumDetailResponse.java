package com.alexcode.photoapp.api.users.PhotoAppApiUsers.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlbumDetailResponse {

    @JsonProperty(value = "album_id")
    private String albumId;

    @JsonProperty(value = "user_id")
    private String userId;

    private String name;

    private String description;
}
