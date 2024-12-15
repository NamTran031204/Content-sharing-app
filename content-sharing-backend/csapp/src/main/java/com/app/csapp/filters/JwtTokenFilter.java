package com.app.csapp.filters;

import com.app.csapp.components.JwtTokenUtil;
import com.app.csapp.models.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    @Value("${api.prefix}")
    private String apiPrefix;

    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try{
            if(isBypassToken(request)) {
                filterChain.doFilter(request, response); //enable bypass
                return;
            }
            final String authHeader = request.getHeader("Authorization"); // header of request

            if(authHeader != null && authHeader.startsWith("Bearer ")) {
                final String token = authHeader.substring(7); // loai bo chuoi "Bearer " o dau chuoi
                final String identifier = jwtTokenUtil.extractIdentifier(token);
                if (identifier != null &&
                        SecurityContextHolder.getContext().getAuthentication() == null //check login?
                ) {
                    User userDetails = (User) userDetailsService.loadUserByUsername(identifier);
                    if (jwtTokenUtil.validateToken(token, userDetails)) {
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails,
                                        null, // credentials nhma minh khong co
                                        userDetails.getAuthorities()
                                );
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
                filterChain.doFilter(request, response); //enable bypass
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        }
    }

    private boolean isBypassToken(@NonNull  HttpServletRequest request) {
        // cho qua cac request nay vi cac request nay khong can token
        final List<Pair<String, String>> bypassTokens = Arrays.asList(
                Pair.of(String.format("%s/tags", apiPrefix), "GET"),
                Pair.of(String.format("%s/tags", apiPrefix), "POST"),
                Pair.of(String.format("%s/tags/**", apiPrefix), "PUT"),

                Pair.of(String.format("%s/pictures**", apiPrefix), "GET"),

                Pair.of(String.format("%s/users/pictureTags/**", apiPrefix), "GET"),

                Pair.of(String.format("%s/boards/user/**/board/**", apiPrefix), "GET"),

                Pair.of(String.format("%s/users/register", apiPrefix), "POST"),
                Pair.of(String.format("%s/users/login", apiPrefix), "POST"),
                Pair.of(String.format("%s/users/**", apiPrefix), "Get")
        );
        // lam sao de an filter vao -> can chan o WebSecurityConfig trong ham SecurityFilterChain

        for(Pair<String, String> bypassToken: bypassTokens) {
            if (request.getServletPath().contains(bypassToken.getFirst()) &&
                    request.getMethod().equals(bypassToken.getSecond())) {
                return true;
            }
        }
        return false;
    }
}
