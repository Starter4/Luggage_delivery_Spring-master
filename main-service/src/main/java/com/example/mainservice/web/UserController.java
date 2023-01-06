package com.example.mainservice.web;

import com.example.mainservice.dto.UserDTO;
import com.example.mainservice.entity.User;
import com.example.mainservice.payload.request.registration.SignupRequest;
import com.example.mainservice.service.serviceInterface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNewUser(SignupRequest signupRequest) {
        return ResponseEntity.ok(userService.createUser(signupRequest));
    }

    @GetMapping("/get/id/{userId}")
    public UserDTO getUserById(@PathVariable long userId) {
        return userService.findUserById(userId).orElse(null);
    }

    @GetMapping("/get/login/{userLogin}")
    public UserDTO getUserByLogin(@PathVariable String userLogin) {
        return userService.findUserByLogin(userLogin).orElse(null);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@ModelAttribute User user) {
        userService.updateUser(user);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/delete/id/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable long userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/login/{userLogin}")
    public ResponseEntity<?> deleteUserByLogin(@PathVariable String userLogin) {
        userService.deleteUserByLogin(userLogin);
        return ResponseEntity.ok().build();
    }
}