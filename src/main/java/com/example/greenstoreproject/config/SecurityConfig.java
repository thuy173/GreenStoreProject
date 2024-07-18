package com.example.greenstoreproject.config;

import com.example.greenstoreproject.filter.JwtRequestFilter;
import com.example.greenstoreproject.service.impl.AuthServiceImpl;
import com.example.greenstoreproject.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthServiceImpl userService;
    private final JwtUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JwtRequestFilter jwtRequestFilter(){
        return new JwtRequestFilter(jwtUtil, userService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/login","/api/register","/api/google-login").permitAll()
                        .requestMatchers("/api/category").permitAll()
                        .requestMatchers("/api/category/{id}").permitAll()
                        .requestMatchers("/api/product").permitAll()
                        .requestMatchers("/api/product/{id}").permitAll()
                        .requestMatchers("/api/nutrient").permitAll()
                        .requestMatchers("/api/nutrient/{id}").permitAll()
                        .requestMatchers("/api/cart/**").permitAll()
                        .requestMatchers("/api/order/**").permitAll()
                        .requestMatchers("/api/address/**").authenticated()
                        .requestMatchers("/api/customer/**").permitAll()
                        .requestMatchers("/api/review/**").permitAll()
                        .requestMatchers("/api/rating/**").permitAll()
                        .requestMatchers("/api/blog/app").permitAll()
                        .requestMatchers("/api/blog/{id}").permitAll()
                        .requestMatchers("/api/voucher/{id}").permitAll()
                        .requestMatchers("/api/voucher/**").permitAll()
                        .requestMatchers("/api/notification").permitAll()
                        .requestMatchers("/ws/**").permitAll()
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
        http.addFilterBefore(jwtRequestFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    private static final String[] AUTH_WHITELIST ={
            "/api/v1/auth/**",
            "/v2/api-docs",
            "/v3/api-docs/**",
            "/v3/api-docs.yaml",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "configuration/ui",
            "configuration/security"
    };
}
