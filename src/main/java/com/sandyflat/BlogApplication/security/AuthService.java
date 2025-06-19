package com.sandyflat.BlogApplication.security;

import com.sandyflat.BlogApplication.dto.LoginRequest;
import com.sandyflat.BlogApplication.dto.RefreshTokenRequest;
import com.sandyflat.BlogApplication.dto.TokenPair;
import com.sandyflat.BlogApplication.dto.UserDTO;
import com.sandyflat.BlogApplication.entity.User;
import com.sandyflat.BlogApplication.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public TokenPair login(LoginRequest loginRequest){
        // TODO Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        // Set authentication in security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate Token Pair
        return jwtService.generateTokenPair(authentication);
    }

    // Generate new access token from refresh token
    public TokenPair refreshToken(@Valid RefreshTokenRequest refreshTokenRequest) {

        String refreshToken = refreshTokenRequest.getRefreshToken();

        // Check if it is valid refresh token
        if(!jwtService.isRefreshToken(refreshToken)){
            throw new IllegalArgumentException("Invalid refresh token");
        }

        String user = jwtService.extractUsernameFromToken(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(user);

        if(userDetails == null){
            throw new IllegalArgumentException("User not found");
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );

        String accessToken = jwtService.generateAccessToken(authentication);
        return new TokenPair(accessToken, refreshToken);
    }

}
