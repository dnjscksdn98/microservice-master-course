package com.alexcode.photoapp.api.albums.PhotoAppApiAlbums.model.response;

import com.alexcode.photoapp.api.albums.PhotoAppApiAlbums.model.entity.AlbumEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class AlbumResponse {

    private String albumId;
    private String userId;
    private String name;
    private String description;

    public static List<AlbumResponse> of(List<AlbumEntity> albums) {
        if(albums == null || albums.isEmpty()) return new ArrayList<>();

        return albums.stream()
                .map(album -> AlbumResponse.builder()
                        .albumId(album.getAlbumId())
                        .userId(album.getUserId())
                        .name(album.getName())
                        .description(album.getDescription())
                        .build())
                .collect(Collectors.toList());
    }
}
