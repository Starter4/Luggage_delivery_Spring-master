package com.example.mainservice.repository;

import com.example.mainservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long > {

    Optional<User> findUserByLogin(String login);

    @Override
    Optional<User> findById(Long aLong);
}
