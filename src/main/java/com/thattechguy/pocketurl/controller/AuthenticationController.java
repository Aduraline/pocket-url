package com.thattechguy.pocketurl.controller;

import com.thattechguy.pocketurl.dto.LoginRequestDto;
import com.thattechguy.pocketurl.dto.SignupRequestDto;
import com.thattechguy.pocketurl.response.ApiResponse;
import com.thattechguy.pocketurl.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signUp(@RequestBody  @Valid SignupRequestDto request) {
        var data = authenticationService.signUp(request);

        ApiResponse response = new ApiResponse();
        response.setSuccess(true);
        response.setMessage("Signup was successful");
        response.setData(data);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody @Valid LoginRequestDto request) {
        var data = authenticationService.login(request);

        ApiResponse response = new ApiResponse();
        response.setSuccess(true);
        response.setMessage("Login was successful");
        response.setData(data);

        return ResponseEntity.ok(response);
    }
}
