package com.artuur.crudaws.config;

import com.artuur.crudaws.enums.Role;
import com.artuur.crudaws.model.User;
import com.artuur.crudaws.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${app.admin.password:admin123}") // Pega do .env ou usa o default
    private String adminPassword;

    public AdminUserConfig(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        var roleAdmin = Role.ADMIN;

        var userAdmin = userRepository.findByUsername("admin");

        userAdmin.ifPresentOrElse(
                (user) -> {
                    System.out.println("admin ja exite!");
                },
                () -> {
                    User user = User.builder()
                            .username("admin")
                            .password(passwordEncoder.encode(adminPassword))
                            .role(roleAdmin)
                            .build();

                    userRepository.save(user);
                }
        );
    }
}
