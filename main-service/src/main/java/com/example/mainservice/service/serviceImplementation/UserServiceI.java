package com.example.mainservice.service.serviceImplementation;

import com.example.mainservice.configurtion.CustomBCryptPasswordEncoder;
import com.example.mainservice.dto.UserDTO;
import com.example.mainservice.entity.ConfirmationToken;
import com.example.mainservice.entity.User;
import com.example.mainservice.entity.enums.Role;
import com.example.mainservice.facade.UserFacade;
import com.example.mainservice.payload.request.registration.SignupRequest;
import com.example.mainservice.repository.UserRepository;
import com.example.mainservice.service.serviceInterface.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceI implements UserService {

    private final UserRepository userRepository;
    private final CustomBCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenServiceI confirmationTokenServiceI;
    private final UserFacade userFacade;

    @Transactional
    public String createUser(SignupRequest signupRequest){
        User user = new User();
        user.setLogin(signupRequest.getEmail());
        user.setFirstName(signupRequest.getFirstname());
        user.setLastName(signupRequest.getLastname());
        user.setUsername(signupRequest.getUsername());
        user.setUserPhone(signupRequest.getPhoneNumber());
        user.setPassword(bCryptPasswordEncoder.passwordEncoder().encode(signupRequest.getPassword()));
        user.setRole(Role.USER);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user
        );

        confirmationTokenServiceI.saveToken(confirmationToken);
        return token;
    }

    @Override
    public Optional<UserDTO> findUserById(long id) {
        return userRepository.findById(id)
                .map(userFacade::convertUserToDTO);
    }

    @Override
    public Optional<UserDTO> findUserByLogin(String login) {
        return userRepository.findUserByLogin(login)
                .map(userFacade::convertUserToDTO);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteUserByLogin(String login) {
        userRepository.deleteByLogin(login);
    }
}