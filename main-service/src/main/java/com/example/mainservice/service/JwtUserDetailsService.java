package com.example.mainservice.service;

import com.example.mainservice.entity.JwtUser;
import com.example.mainservice.entity.User;
import com.example.mainservice.entity.enums.Role;
import com.example.mainservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findUserByLogin(login)
                .orElseThrow(()-> new UsernameNotFoundException(String.format("User with login '%s' dose not find",login)));
        return buildUser(user);
    }

    public JwtUser findUserByID(Long id){
        User user = userRepository.findById(id)
                .orElse(null);
        return buildUser(Objects.requireNonNull(user));
    }

    private Collection<? extends GrantedAuthority> mapRoleToAuthority(Role role){
        return List.of(new SimpleGrantedAuthority(role  .getAuthority()));
    }

    private JwtUser buildUser(User user){
        return new JwtUser(user.getId()
                , user.getUsername()
                , user.getFirstName()
                , user.getLastName()
                , user.getLogin()
                , user.getPassword()
                , mapRoleToAuthority(user.getRole())
                , user.isActive()
                , user.getLastOnline());
    }
}
