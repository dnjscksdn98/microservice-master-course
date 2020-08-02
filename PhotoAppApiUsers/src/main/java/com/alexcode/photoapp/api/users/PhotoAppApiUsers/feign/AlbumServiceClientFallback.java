package com.alexcode.photoapp.api.users.PhotoAppApiUsers.feign;

import com.alexcode.photoapp.api.users.PhotoAppApiUsers.model.response.AlbumDetailResponse;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class AlbumServiceClientFallback implements AlbumServiceClient {

    private final Throwable cause;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public AlbumServiceClientFallback(Throwable cause) {
        this.cause = cause;
    }

    @Override
    public List<AlbumDetailResponse> getAlbums(String userId) {
//        if(cause instanceof FeignException && ((FeignException) cause).status() == 404) {
//            logger.error("404 error took place when getAlbums() was called with userId: "
//                    + userId + ", Error message: "
//                    + cause.getLocalizedMessage());
//        }
        logger.error("An exception took place: " + cause.toString());

        return new ArrayList<>();
    }
}
