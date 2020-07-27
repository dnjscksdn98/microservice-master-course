package com.alexcode.photoapp.api.users.PhotoAppApiUsers.repository;

import com.alexcode.photoapp.api.users.PhotoAppApiUsers.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
