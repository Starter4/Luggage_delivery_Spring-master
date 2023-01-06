package com.example.mainservice.facade;

import com.example.mainservice.dto.UserDTO;
import com.example.mainservice.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserFacade {
    public UserDTO convertUserToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .login(user.getLogin())
                .lastOnline(user.getLastOnline())
                .userPhone(user.getUserPhone())
                .age(user.getAge())
                .enable(user.isActive())
                .userRole(user.getRole())
                .build();
    }
}