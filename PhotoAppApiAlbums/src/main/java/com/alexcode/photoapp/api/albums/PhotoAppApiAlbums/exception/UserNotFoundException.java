package com.alexcode.photoapp.api.albums.PhotoAppApiAlbums.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String userId) {
        super(String.format("User not found with id: %s", userId));
    }
}
