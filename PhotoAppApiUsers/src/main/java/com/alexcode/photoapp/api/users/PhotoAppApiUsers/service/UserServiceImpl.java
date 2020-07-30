package com.alexcode.photoapp.api.users.PhotoAppApiUsers.service;

import com.alexcode.photoapp.api.users.PhotoAppApiUsers.model.dto.UserDto;
import com.alexcode.photoapp.api.users.PhotoAppApiUsers.model.entity.UserEntity;
import com.alexcode.photoapp.api.users.PhotoAppApiUsers.model.response.AlbumDetailResponse;
import com.alexcode.photoapp.api.users.PhotoAppApiUsers.repository.UserRepository;
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
    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(Environment env, RestTemplate restTemplate, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.env = env;
        this.restTemplate = restTemplate;
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

        String albumUrl = String.format(env.getProperty("albums.path"), userId);

        ResponseEntity<List<AlbumDetailResponse>> response = restTemplate.exchange(
                albumUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AlbumDetailResponse>>() {});

        List<AlbumDetailResponse> albums = response.getBody();

        return UserDto.of(userEntity, albums);
    }
}
