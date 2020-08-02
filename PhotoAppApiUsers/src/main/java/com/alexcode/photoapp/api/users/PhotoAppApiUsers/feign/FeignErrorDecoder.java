package com.alexcode.photoapp.api.users.PhotoAppApiUsers.feign;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ResponseStatusException;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

    private final Environment env;

    @Autowired
    public FeignErrorDecoder(Environment env) {
        this.env = env;
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        HttpStatus statusCode = HttpStatus.valueOf(response.status());
        String statusText = response.reason();

//        switch(response.status()) {
//            case 400:
//                break;
//
//            case 404:
//                if(methodKey.contains("getAlbums")) {
//                    return new ResponseStatusException(HttpStatus.valueOf(response.status()), response.reason());
//                }
//                break;
//
//            default:
//                return new Exception(response.reason());
//        }
        if (response.status() >= 400 && response.status() <= 499) {
            return new HttpClientErrorException(statusCode, statusText);
        }

        if (response.status() >= 500 && response.status() <= 599) {
            return new HttpServerErrorException(statusCode, statusText);
        }
        return null;
    }
}
