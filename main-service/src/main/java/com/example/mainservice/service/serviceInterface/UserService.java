package com.example.mainservice.service.serviceInterface;

import com.example.mainservice.entity.User;
import com.example.mainservice.payload.request.registration.SignupRequest;

import java.util.Optional;

public interface UserService {

    public String createUser(SignupRequest signupRequest);

    public Optional<User> findUserById(long id);
    public Optional<User> findUserByLogin(String login);

    public void updateUser(User user);
    public void deleteUserById(long id);
    public void deleteUserByLogin(String login);
}
