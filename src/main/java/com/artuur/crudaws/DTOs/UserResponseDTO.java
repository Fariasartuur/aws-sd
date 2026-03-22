package com.artuur.crudaws.DTOs;

import com.artuur.crudaws.enums.Role;
import com.artuur.crudaws.model.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserResponseDTO(Long id, @NotBlank String username, @NotNull Role role) {

    public UserResponseDTO(User user) {
        this(user.getId(), user.getUsername(), user.getRole());
    }

}
