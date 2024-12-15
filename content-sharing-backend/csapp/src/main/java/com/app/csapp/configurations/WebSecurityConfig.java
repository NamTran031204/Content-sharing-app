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
                            ) // chi cho phep dang ky va dang nhap
                            .permitAll()
                            .requestMatchers(
                                    PUT, String.format("%s/tags**", apiPrefix)).hasAnyRole(Role.ADMIN)
                            .requestMatchers(
                                    DELETE, String.format("%s/tags**", apiPrefix)).hasAnyRole(Role.ADMIN)

                            .requestMatchers(
                                    POST, String.format("%s/pictures", apiPrefix)).hasAnyRole(Role.USER)
                            .requestMatchers(
                                    POST, String.format("%s/pictures/uploads", apiPrefix)).hasAnyRole(Role.USER)
                            .requestMatchers(
                                    PUT, String.format("%s/pictures", apiPrefix)).hasAnyRole(Role.USER) // de sau
                            .requestMatchers(
                                    DELETE, String.format("%s/pictures/**", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN)

                            .requestMatchers(
                                    POST, String.format("%s/pictureTags", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN)
                            .requestMatchers(
                                    PUT, String.format("%s/pictureTags/**", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN)
                            .requestMatchers(
                                    DELETE, String.format("%s/picturesTags/**", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN)

                            .requestMatchers(
                                    POST, String.format("%s/boards", apiPrefix)).hasAnyRole(Role.USER)
                            .requestMatchers(
                                    PUT, String.format("%s/boards/user/**/board/**", apiPrefix)).hasAnyRole(Role.USER)
                            .requestMatchers(
                                    DELETE, String.format("%s/boards/**", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN)

                            .requestMatchers(
                                    POST, String.format("%s/follower/follow", apiPrefix)).hasAnyRole(Role.USER)
                            .requestMatchers(
                                    DELETE, String.format("%s/follower/unfollow", apiPrefix)).hasAnyRole(Role.USER)
                            .requestMatchers(
                                    GET, String.format("%s/follower_list/**", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN)
                            .requestMatchers(
                                    GET, String.format("%s/following_list/**", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN)

                            .requestMatchers(
                                    POST, String.format("%s/reacts", apiPrefix)).hasAnyRole(Role.USER)
                            .requestMatchers(
                                    DELETE, String.format("%s/reacts/**", apiPrefix)).hasAnyRole(Role.USER)
                            .requestMatchers(
                                    GET, String.format("%s/reacts/**", apiPrefix)).hasAnyRole(Role.USER, Role.ADMIN)

                            .requestMatchers(
                                    PUT, String.format("%s/users/update/infor/**", apiPrefix)).hasAnyRole(Role.USER)
                            .requestMatchers(
                                    PUT, String.format("%s/users/update/**", apiPrefix)).hasAnyRole(Role.USER)
                            .requestMatchers(
                                    DELETE, String.format("%s/users/**", apiPrefix)).hasAnyRole(Role.USER)
                            .anyRequest().authenticated();
                });
        return http.build();
    }
}
