package com.dkwasniewski.aprimons.service;

import com.dkwasniewski.aprimons.model.User;
import com.dkwasniewski.aprimons.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User findUserByUsername(String name) {
        return userRepository.findUserByUsername(name);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userRepository.findUserByUsername(username);

        return org.springframework.security.core.userdetails.User
                .withUsername(userEntity.getUsername())
                .password(userEntity.getPassword())
                .roles(userEntity.getRole())
                .build();
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findUserByUsername(authentication.getName());
    }

    public void activateUser(User user) {
        user.setActive(true);
        userRepository.save(user);
    }
}
