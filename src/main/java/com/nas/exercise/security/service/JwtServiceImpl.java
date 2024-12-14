package com.nas.exercise.security.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.nas.exercise.security.domain.service.JwtService;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {
    //https://generate.plus/es/hex/secret-key
    private static final String SECRET_KEY="oCTbzxS4YvQjAzOlZmHXFFTKeiC8eJ6AeF5CQk1PfFncMMZQmsWlIvD2B8a6l6jG7K7bOBOy6Cb3jhqGt3NGMg";

    @Override
    public String getToken(UserDetails user) {
        return getToken(new HashMap<>(), user);
    }
    
    @Override
    public String getToken(HashMap<String,Object> extraClaims, UserDetails user) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public Key getKey(){
        byte[] keyBytes= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public String getEmailFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String email=getEmailFromToken(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    @Override
    public Claims getAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public <T> T getClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims=getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public Date getExpiration(String token){
        return getClaim(token, Claims::getExpiration);
    }

    @Override
    public boolean isTokenExpired(String token){
        return getExpiration(token).before(new Date());
    }
}
