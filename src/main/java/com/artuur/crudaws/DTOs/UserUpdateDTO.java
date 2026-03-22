package com.artuur.crudaws.DTOs;

import jakarta.validation.constraints.NotBlank;

public record UserUpdateDTO(@NotBlank String username) {
}
