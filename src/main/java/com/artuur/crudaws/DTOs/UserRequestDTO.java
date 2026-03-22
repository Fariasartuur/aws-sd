package com.artuur.crudaws.DTOs;

import com.artuur.crudaws.model.User;
import jakarta.validation.constraints.NotBlank;

public record UserRequestDTO(@NotBlank String username, @NotBlank String password) {

    public UserRequestDTO(User user) {
        this(user.getUsername(), user.getPassword());
    }
}
