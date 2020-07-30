package com.alexcode.photoapp.api.albums.PhotoAppApiAlbums.model.entity;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlbumEntity {

    private Long id;
    private String albumId;
    private String userId;
    private String name;
    private String description;
}
