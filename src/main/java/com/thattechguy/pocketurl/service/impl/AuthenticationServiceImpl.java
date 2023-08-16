package com.thattechguy.pocketurl.service.impl;

import com.thattechguy.pocketurl.dto.LoginRequestDto;
import com.thattechguy.pocketurl.dto.SignupRequestDto;
import com.thattechguy.pocketurl.model.Role;
import com.thattechguy.pocketurl.model.User;
import com.thattechguy.pocketurl.repository.UserRepository;
import com.thattechguy.pocketurl.response.auth.AuthenticationResponse;
import com.thattechguy.pocketurl.security.CustomUserDetails;
import com.thattechguy.pocketurl.security.JwtService;
import com.thattechguy.pocketurl.service.AuthenticationService;
import com.thattechguy.pocketurl.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse signUp(SignupRequestDto request) {
        var user = User
                .builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        var token = jwtService.generateToken(
                new CustomUserDetails(
                        user.getId(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getAuthorities()
                )
        );

        return AuthenticationResponse
                .builder()
                .token(token)
                .build();
    }

    @Override
    public AuthenticationResponse login(LoginRequestDto request) {
        var user = userRepository
                .findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new UserNotFoundException("User not found")
                );

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var token = jwtService.generateToken(
                new CustomUserDetails(
                        user.getId(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getAuthorities()
                )
        );

        return AuthenticationResponse
                .builder()
                .token(token)
                .build();
    }
}
