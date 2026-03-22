package com.artuur.crudaws.model;

import com.artuur.crudaws.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@Setter
@Builder
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String username;

    @NotBlank
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    public boolean isLoginCorrect(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password, this.password);
    }
}
