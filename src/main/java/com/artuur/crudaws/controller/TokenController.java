package com.artuur.crudaws.controller;

import com.artuur.crudaws.DTOs.LoginResponseDTO;
import com.artuur.crudaws.DTOs.UserRequestDTO;
import com.artuur.crudaws.repository.UserRepository;
import com.artuur.crudaws.services.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenController {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/auth/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody UserRequestDTO loginRequest) {

        var user = userRepository.findByUsername(loginRequest.username())
                .orElseThrow(() -> new BadCredentialsException("Usuário não encontrado"));

        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            throw new BadCredentialsException("Senha inválida");
        }

        var token = tokenService.generateToken(user);

        return ResponseEntity.ok(new LoginResponseDTO(token, 3600L));
    }
}
