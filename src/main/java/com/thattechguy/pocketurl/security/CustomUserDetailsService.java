package com.thattechguy.pocketurl.security;

import com.thattechguy.pocketurl.model.User;
import com.thattechguy.pocketurl.repository.UserRepository;
import com.thattechguy.pocketurl.exception.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private CustomUserDetails buildCustomUserDetails(User user) {
        return new CustomUserDetails(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities()
        );
    }

    public CustomUserDetails loadUserByUserId(String userId) {
        User user = userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return buildCustomUserDetails(user);
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return buildCustomUserDetails(user);
    }
}
