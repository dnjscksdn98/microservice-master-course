package com.alexcode.photoapp.api.users.PhotoAppApiUsers.feign;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class AlbumsFallbackFactory implements FallbackFactory<AlbumServiceClient> {

    @Override
    public AlbumServiceClient create(Throwable cause) {
        return new AlbumServiceClientFallback(cause);
    }
}
