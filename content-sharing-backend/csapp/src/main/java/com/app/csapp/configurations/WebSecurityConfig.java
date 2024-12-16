package com.app.csapp.configurations;

import com.app.csapp.filters.JwtTokenFilter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import com.app.csapp.models.Role;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtTokenFilter jwtTokenFilter;
    @Value("${api.prefix}")
    private String apiPrefix;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                // chan tat ca cac request khong co token
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> {
                    requests
                            .requestMatchers(
                                    String.format("%s/users/register", apiPrefix),
                                    String.format("%s/users/login", apiPrefix)

                            ).permitAll()
                            .requestMatchers(
                                    GET, String.format("%s/users", apiPrefix)).permitAll()
                            .requestMatchers(
                                    GET, String.format("%s/tags", apiPrefix)).permitAll()
                            .requestMatchers(
                                    GET, String.format("%s/reacts", apiPrefix)).permitAll()
                            .requestMatchers(
                                    GET, String.format("%s/pictures", apiPrefix)).permitAll()
                            .requestMatchers(
                                    GET, String.format("%s/pictures/getPicture/**", apiPrefix)).permitAll()
                            .requestMatchers(
                                    GET, String.format("%s/boards", apiPrefix)).permitAll()
                            .requestMatchers(POST, "/api/v1/pictures/uploads/**").permitAll()
                            .anyRequest().authenticated();
                });
        return http.build();
    }
}
