package com.alexcode.photoapp.api.users.PhotoAppApiUsers.service;

import com.alexcode.photoapp.api.users.PhotoAppApiUsers.model.dto.UserDto;
import com.alexcode.photoapp.api.users.PhotoAppApiUsers.model.entity.UserEntity;
import com.alexcode.photoapp.api.users.PhotoAppApiUsers.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        UserEntity userEntity = UserEntity.of(userDto);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        return UserDto.of(savedUserEntity);
    }
}
