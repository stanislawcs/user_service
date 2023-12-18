package com.example.code.services.impl;

import com.example.code.details.DefaultUserDetails;
import com.example.code.domain.User;
import com.example.code.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DefaultUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> foundedUser = userRepository.findByUsername(username);

        if (foundedUser.isEmpty()) {
            throw new UsernameNotFoundException("Failed to retrieve user" + username);
        }

        return new DefaultUserDetails(foundedUser.get());
    }
}
