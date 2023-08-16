package com.thattechguy.pocketurl.service;

import com.thattechguy.pocketurl.dto.LoginRequestDto;
import com.thattechguy.pocketurl.dto.SignupRequestDto;
import com.thattechguy.pocketurl.response.auth.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse signUp(SignupRequestDto request);
    AuthenticationResponse login(LoginRequestDto request);
}
