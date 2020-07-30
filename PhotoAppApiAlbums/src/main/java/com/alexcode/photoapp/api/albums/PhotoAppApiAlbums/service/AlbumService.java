package com.alexcode.photoapp.api.albums.PhotoAppApiAlbums.service;

import com.alexcode.photoapp.api.albums.PhotoAppApiAlbums.model.entity.AlbumEntity;

import java.util.List;

public interface AlbumService {

    List<AlbumEntity> getAlbums(String userId);
}
