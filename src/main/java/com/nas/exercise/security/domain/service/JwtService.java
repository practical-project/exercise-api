package com.nas.exercise.security.domain.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

public interface JwtService {
    String getToken(UserDetails user);
    String getToken(HashMap<String,Object> extraClaims, UserDetails user);
    Key getKey();
    String getEmailFromToken(String token);
    boolean isTokenValid(String token, UserDetails userDetails);
    Claims getAllClaims(String token);
    <T> T getClaim(String token, Function<Claims,T> claimsResolver);
    Date getExpiration(String token);
    boolean isTokenExpired(String token);
}
