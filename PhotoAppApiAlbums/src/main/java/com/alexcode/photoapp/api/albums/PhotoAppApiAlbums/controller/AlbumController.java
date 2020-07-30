package com.alexcode.photoapp.api.albums.PhotoAppApiAlbums.controller;

import com.alexcode.photoapp.api.albums.PhotoAppApiAlbums.model.entity.AlbumEntity;
import com.alexcode.photoapp.api.albums.PhotoAppApiAlbums.model.response.AlbumResponse;
import com.alexcode.photoapp.api.albums.PhotoAppApiAlbums.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "users/{userId}/albums")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<AlbumResponse> getAlbums(@PathVariable("userId") String userId) {
        List<AlbumEntity> albums = albumService.getAlbums(userId);
        return AlbumResponse.of(albums);
    }
}
