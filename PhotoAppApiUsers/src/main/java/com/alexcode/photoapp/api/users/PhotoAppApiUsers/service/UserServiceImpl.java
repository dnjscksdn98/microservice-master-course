package com.alexcode.photoapp.api.users.PhotoAppApiUsers.service;

import com.alexcode.photoapp.api.users.PhotoAppApiUsers.feign.AlbumServiceClient;
import com.alexcode.photoapp.api.users.PhotoAppApiUsers.model.dto.UserDto;
import com.alexcode.photoapp.api.users.PhotoAppApiUsers.model.entity.UserEntity;
import com.alexcode.photoapp.api.users.PhotoAppApiUsers.model.response.AlbumDetailResponse;
import com.alexcode.photoapp.api.users.PhotoAppApiUsers.repository.UserRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final Environment env;
//    private final RestTemplate restTemplate;
    private final AlbumServiceClient albumServiceClient;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserServiceImpl(Environment env, AlbumServiceClient albumServiceClient, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.env = env;
        this.albumServiceClient = albumServiceClient;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setEncryptedPassword(passwordEncoder.encode(userDto.getPassword()));
        UserEntity userEntity = UserEntity.of(userDto);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        return UserDto.of(savedUserEntity);
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        return UserDto.of(userEntity);
    }

    @Override
    public UserDto getUserById(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException(userId));

        // 1. RestTemplate
//        String albumUrl = String.format(env.getProperty("albums.path"), userId);
//
//        ResponseEntity<List<AlbumDetailResponse>> response = restTemplate.exchange(
//                albumUrl,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<AlbumDetailResponse>>() {});
//
//        List<AlbumDetailResponse> albums = response.getBody();

        // 2. Feign Client
        logger.info("Before calling albums Microservice");
        List<AlbumDetailResponse> albums = albumServiceClient.getAlbums(userId);
        logger.info("After calling albums Microservice");

        return UserDto.of(userEntity, albums);
    }
}
