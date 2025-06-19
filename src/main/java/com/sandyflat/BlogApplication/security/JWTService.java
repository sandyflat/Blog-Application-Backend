package com.sandyflat.BlogApplication.security;


import com.sandyflat.BlogApplication.dto.TokenPair;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class JWTService {
    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration}")
    private long jwtExpirationMs;

    @Value("${app.jwt.refresh-expiration}")
    private long refreshExpirationMs;

    public TokenPair generateTokenPair(Authentication authentication){
        String accessToken = generateAccessToken(authentication);
        String refreshToken = generateRefreshToken(authentication);

        return new TokenPair(accessToken,refreshToken);
    }

    // TODO Generate access token
    public String generateAccessToken(Authentication authentication){
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        Date now = new Date();    // Time of token creation
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);  // Time of token expiration

        return Jwts.builder()
                .header()
                .add("typ","JWT")
                .and()
                .subject(userPrincipal.getUsername())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSignInKey())
                .compact();
    }

    // TODO Generate Refresh token
    public String generateRefreshToken(Authentication authentication){
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

        Date now = new Date();    // Time of token creation
        Date expiryDate = new Date(now.getTime() + refreshExpirationMs);  // Time of token expiration

        Map<String, String> claims = new HashMap<>();
        claims.put("tokenType", "refresh");

        return Jwts.builder()
                .header()
                .add("typ","JWT")
                .and()
                .subject(userPrincipal.getUsername())
                .claims(claims)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSignInKey())
                .compact();
    }

    // TODO Validate token
    public boolean isValidToken(String token, UserDetails userDetails){
        final String username = extractUsernameFromToken(token);

        if(!username.equals(userDetails.getUsername())){
            return false;
        }

        try{
            Jwts.parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        }catch(SignatureException e){
            log.error("Invalid JWT Signature: {}", e.getMessage());
        }catch (MalformedJwtException e){
            log.error("Invalid JWT token: {}", e.getMessage());
        }catch(ExpiredJwtException e){
            log.error("JWT token is expired: {}", e.getMessage());
        }catch(UnsupportedJwtException e){
            log.error("JWT token is unsupported: {}", e.getMessage());
        }catch(IllegalArgumentException e){
            log.error("JWT Claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    // TODO Validate if the token is refresh token
    public boolean isRefreshToken(String token){
        Claims claims = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return "refresh".equals(claims.get("tokenType"));
    }

    public String extractUsernameFromToken(String token){
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
