package com.artuur.crudaws.services;

import com.artuur.crudaws.DTOs.UserRequestDTO;
import com.artuur.crudaws.model.User;
import com.artuur.crudaws.repository.UserRepository;
import lombok.Value;
import org.apache.catalina.Role;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenService {

    private final JwtEncoder jwtEncoder;


    public TokenService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generateToken(User user) {
        Instant now = Instant.now();
        long expiresIn = 3600L;
        var scope = user.getRole().name();

        var claims = JwtClaimsSet.builder()
                .issuer("backend")
                .subject(user.getUsername())
                .expiresAt(now.plusSeconds(expiresIn))
                .issuedAt(now)
                .claim("scope", scope)
                .build();

        return  jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

}
