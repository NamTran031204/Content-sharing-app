package com.app.csapp.components;


import java.security.Key;
import java.security.SecureRandom;
import java.util.*;
import java.util.Base64.Decoder;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.app.csapp.exceptions.InvalidParamException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
    @Value("${jwt.expiration}")
    private int expiration; // thời gian sống của token - luu vao bien moi truong (yml)
    @Value("${jwt.secretKey}")
    private String secretKey;

    public String generateToken(com.app.csapp.models.User user) throws Exception {
        // save properties as claims
        Map<String, Object> claims = new HashMap<>();
        //this.generateSecretKey();
        String identifier;
        if (user.getPhoneNumber() == null){
            identifier = user.getEmail();
        }else
            identifier = user.getPhoneNumber();
        claims.put("identifier", identifier);
        try {
            String token = Jwts.builder()
                    .setClaims(claims) // day claim vao thi phai lay ra => extract claim from token
                    .setSubject(identifier)
                    .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000L))
                    .signWith(getSigninKey(), SignatureAlgorithm.HS256) // secret key để mã hóa token
                    .compact();
            return token;
        } catch (Exception e) {
            throw new InvalidParamException("Cannot create jwt token, error: "+e.getMessage());
        }
    }

    private Key getSigninKey() {
        System.out.println(secretKey);
        byte[] bytes = Decoders.BASE64.decode(secretKey); // giải mã secret key
        // Keys.hmacShaKeyFor(Decoders.BASE64.decode("nprtI/yUVAZiaZBTM7K1CYqEkrvXOPyeOIFq/1wCFZE="))
        return Keys.hmacShaKeyFor(bytes);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = this.extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = this.extractClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }

    //get subject
    public String extractIdentifier(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // kiem tra token con khong
    public boolean validateToken(String token, UserDetails userDetails) {
        final String identifier = extractIdentifier(token);
        return (identifier.equals(userDetails.getUsername())
                && !isTokenExpired(token));
    }

    //generate SecretKey - used once
    private String generateSecretKey(){
        SecureRandom random = new SecureRandom();
        byte[] keyBytes = new byte[32];
        random.nextBytes(keyBytes);
        String secretKey = Encoders.BASE64.encode(keyBytes);
        return secretKey;
    }
}
