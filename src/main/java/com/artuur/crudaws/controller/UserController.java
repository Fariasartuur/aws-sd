package com.artuur.crudaws.controller;

import com.artuur.crudaws.DTOs.UserRequestDTO;
import com.artuur.crudaws.DTOs.UserResponseDTO;
import com.artuur.crudaws.DTOs.UserUpdateDTO;
import com.artuur.crudaws.enums.Role;
import com.artuur.crudaws.model.User;
import com.artuur.crudaws.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserController(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.repository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getUsers() {
        List<UserResponseDTO> list = repository.findAll().stream().map(UserResponseDTO::new).toList();

        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody @Valid UserRequestDTO user) {
        User newUser = User.builder()
                .username(user.username())
                .password(passwordEncoder.encode(user.password()))
                .role(Role.USER)
                .build();

        repository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody @Valid UserUpdateDTO user) {
        User usuario = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado"));

        usuario.setUsername(user.username());

        repository.save(usuario);

        return ResponseEntity.ok(new UserResponseDTO(usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean user = repository.existsById(id);

        if(!user) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado");
        }

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
