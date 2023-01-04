package com.example.mainservice.web;

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
    public ResponseEntity<?> getUserById(@PathVariable long userId) {
        return ResponseEntity.ok(userService.findUserById(userId).orElse(null));
    }

    @GetMapping("/get/login/{userLogin}")
    public ResponseEntity<?> getUserByLogin(@PathVariable String userLogin) {
        return ResponseEntity.ok(userService.findUserByLogin(userLogin).orElse(null));
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateUser(@ModelAttribute User user) {
        userService.updateUser(user);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/delete/id/{userId}")
    public ResponseEntity<?> deleteUserById(@PathVariable long userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete/login/{userLogin}")
    public ResponseEntity<?> deleteUserByLogin(@PathVariable String userLogin) {
        userService.deleteUserByLogin(userLogin);
        return ResponseEntity.ok().build();
    }
}