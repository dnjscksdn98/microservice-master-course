package com.alexcode.photoapp.api.users.PhotoAppApiUsers.repository;

import com.alexcode.photoapp.api.users.PhotoAppApiUsers.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity save(UserEntity userEntity);

    Optional<UserEntity> findByEmail(String username);

    Optional<UserEntity> findByUserId(String userId);
}
