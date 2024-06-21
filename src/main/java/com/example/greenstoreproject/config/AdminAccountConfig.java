package com.example.greenstoreproject.config;

import com.example.greenstoreproject.entity.Customers;
import com.example.greenstoreproject.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AdminAccountConfig {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            String adminEmail = "admin@gmail.com";
            if (customerRepository.findByEmail(adminEmail) == null) {
                Customers admin = new Customers();
                admin.setEmail(adminEmail);
                admin.setPassword(passwordEncoder.encode("Admin*123"));
                customerRepository.save(admin);
            }
        };
    }
}
