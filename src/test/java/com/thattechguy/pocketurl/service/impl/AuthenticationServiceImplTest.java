package com.thattechguy.pocketurl.service.impl;

import com.thattechguy.pocketurl.dto.LoginRequestDto;
import com.thattechguy.pocketurl.dto.SignupRequestDto;
import com.thattechguy.pocketurl.exception.UserNotFoundException;
import com.thattechguy.pocketurl.model.Role;
import com.thattechguy.pocketurl.model.User;
import com.thattechguy.pocketurl.repository.UserRepository;
import com.thattechguy.pocketurl.security.CustomUserDetails;
import com.thattechguy.pocketurl.security.JwtService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {
    @InjectMocks
    private AuthenticationServiceImpl underTest;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    void testSignUp() {
        // given
        String username = "username";
        String email = "test@test.com";
        String password = "password";

        SignupRequestDto signupRequestDto = new SignupRequestDto(
                username,
                email,
                password
        );

        // when
        underTest.signUp(signupRequestDto);

        // then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        ArgumentCaptor<CustomUserDetails> customUserDetailsArgumentCaptor =
                ArgumentCaptor.forClass(CustomUserDetails.class);

        verify(userRepository).save(userArgumentCaptor.capture());
        verify(passwordEncoder).encode(signupRequestDto.getPassword());
        verify(jwtService).generateToken(customUserDetailsArgumentCaptor.capture());
    }

    @Test
    void testLogin() {
        // given
        String email = "test@test.com";
        String password = "encodedPassword";

        LoginRequestDto loginRequestDto = new LoginRequestDto(
                email,
                password
        );

        User user = User.builder()
                .email(email)
                .password(password)
                .role(Role.USER)
                .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // when
        underTest.login(loginRequestDto);

        // then
        ArgumentCaptor<CustomUserDetails> customUserDetailsArgumentCaptor =
                ArgumentCaptor.forClass(CustomUserDetails.class);
        ArgumentCaptor<UsernamePasswordAuthenticationToken> usernamePasswordAuthenticationTokenArgumentCaptor =
                ArgumentCaptor.forClass(UsernamePasswordAuthenticationToken.class);

        verify(userRepository).findByEmail(loginRequestDto.getEmail());
        verify(authenticationManager).authenticate(usernamePasswordAuthenticationTokenArgumentCaptor.capture());
        verify(jwtService).generateToken(customUserDetailsArgumentCaptor.capture());
    }

    @Test
    void testUserNotFoundForLogin() {
        // given
        String email = "test@test.com";
        String password = "encodedPassword";

        LoginRequestDto loginRequestDto = new LoginRequestDto(
                email,
                password
        );

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // when & then
        assertThrows(UserNotFoundException.class, () -> underTest.login(loginRequestDto));
    }
}