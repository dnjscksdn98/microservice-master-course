package com.alexcode.photoapp.api.users.PhotoAppApiUsers.feign;

import com.alexcode.photoapp.api.users.PhotoAppApiUsers.model.response.AlbumDetailResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AlbumsFallback implements AlbumServiceClient {

    @Override
    public List<AlbumDetailResponse> getAlbums(String userId) {
        return new ArrayList<>();
    }
}
