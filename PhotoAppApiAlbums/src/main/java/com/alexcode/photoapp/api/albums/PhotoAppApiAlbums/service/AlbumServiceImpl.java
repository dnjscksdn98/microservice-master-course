package com.alexcode.photoapp.api.albums.PhotoAppApiAlbums.service;

import com.alexcode.photoapp.api.albums.PhotoAppApiAlbums.exception.UserNotFoundException;
import com.alexcode.photoapp.api.albums.PhotoAppApiAlbums.model.entity.AlbumEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AlbumServiceImpl implements AlbumService{

    @Override
    public List<AlbumEntity> getAlbums(String userId) {
        List<AlbumEntity> albumEntities = new ArrayList<>();

        AlbumEntity albumEntity = AlbumEntity.builder()
                .id(1L)
                .userId(userId)
                .albumId(UUID.randomUUID().toString())
                .name("test album 1")
                .description("test album 1 description")
                .build();

        AlbumEntity albumEntity2 = AlbumEntity.builder()
                .id(2L)
                .userId(userId)
                .albumId(UUID.randomUUID().toString())
                .name("test album 2")
                .description("test album 2 description")
                .build();

        albumEntities.add(albumEntity);
        albumEntities.add(albumEntity2);

        return albumEntities;
    }
}
