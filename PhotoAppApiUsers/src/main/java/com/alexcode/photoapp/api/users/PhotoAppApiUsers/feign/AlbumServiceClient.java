package com.alexcode.photoapp.api.users.PhotoAppApiUsers.feign;

import com.alexcode.photoapp.api.users.PhotoAppApiUsers.model.response.AlbumDetailResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "albums-ws", fallback = AlbumsFallback.class)
public interface AlbumServiceClient {

    @GetMapping(path = "users/{userId}/albums")
    List<AlbumDetailResponse> getAlbums(@PathVariable("userId") String userId);
}
