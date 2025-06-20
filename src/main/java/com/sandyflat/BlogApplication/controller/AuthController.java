package com.sandyflat.BlogApplication.controller;

import com.sandyflat.BlogApplication.dto.LoginRequest;
import com.sandyflat.BlogApplication.dto.RefreshTokenRequest;
import com.sandyflat.BlogApplication.dto.TokenPair;
import com.sandyflat.BlogApplication.dto.UserDTO;
import com.sandyflat.BlogApplication.security.AuthService;
import com.sandyflat.BlogApplication.serviceimpl.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserServiceImpl userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest){
        TokenPair tokenPair = authService.login(loginRequest);
        return ResponseEntity.ok(tokenPair);
    }

    @PostMapping("/register-user")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.createUser(userDTO));
    }

    // Generate new access token from refresh token
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest){
        TokenPair tokenPair = authService.refreshToken(refreshTokenRequest);
        return ResponseEntity.ok(tokenPair);
    }
}
