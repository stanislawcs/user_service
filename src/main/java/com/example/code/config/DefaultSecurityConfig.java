package com.example.code.config;

import com.example.code.config.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class DefaultSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    @SneakyThrows
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {

        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(
                url ->
                        url.requestMatchers("/auth/registration", "/auth/login").permitAll()
                                .requestMatchers(HttpMethod.GET, "/users")
                                .hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/users/{id}")
                                .hasAnyRole("ADMIN", "MODERATOR", "READER")
                                .requestMatchers(HttpMethod.POST, "/users")
                                .hasAnyRole("ADMIN", "MODERATOR", "READER")
                                .requestMatchers(HttpMethod.PUT, "/users/**")
                                .hasAnyRole("ADMIN", "MODERATOR", "READER")
                                .requestMatchers(HttpMethod.DELETE, "/users/**")
                                .hasAnyRole("ADMIN", "MODERATOR", "READER")
                                .anyRequest()
                                .authenticated()
        );

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
