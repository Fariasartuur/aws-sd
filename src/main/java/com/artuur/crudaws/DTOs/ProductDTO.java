package com.artuur.crudaws.DTOs;

import com.artuur.crudaws.model.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record ProductDTO(Long id, @NotBlank String name, @Positive Double price) {

    public ProductDTO(Product product) {
        this(product.getId(), product.getName(), product.getPrice());
    }
}
