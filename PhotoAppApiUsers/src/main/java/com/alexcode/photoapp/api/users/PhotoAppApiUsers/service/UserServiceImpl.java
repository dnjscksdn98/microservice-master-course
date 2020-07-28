package com.alexcode.photoapp.api.users.PhotoAppApiUsers.service;

import com.alexcode.photoapp.api.users.PhotoAppApiUsers.model.dto.UserDto;
import com.alexcode.photoapp.api.users.PhotoAppApiUsers.model.entity.UserEntity;
import com.alexcode.photoapp.api.users.PhotoAppApiUsers.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
    }

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
}
