package com.welldev.ams.service.impl;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.welldev.ams.model.db.Users;
import com.welldev.ams.service.JWTService;

@Slf4j
@Service
public class JWTServiceImpl implements JWTService {

  private final String SECRET_KEY = "your-super-secret-key-that-is-long-and-secure";

  @Override
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  @Override
  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  @Override
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  @Override
  public Claims extractAllClaims(String token) {
    return Jwts.parser().setSigningKey(getSignInKey()).build().parseSignedClaims(token).getPayload();
  }

  private SecretKey getSignInKey() {
    byte[] keyBytes = SECRET_KEY.getBytes();
    return Keys.hmacShaKeyFor(keyBytes);
  }

  @Override
  public Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  @Override
  public String generateToken(Users userDetails) {
    return Jwts.builder()
        .setSubject(userDetails.getEmail())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2)) // 2 hours
        .signWith(getSignInKey(), SignatureAlgorithm.HS256) // Use the Key object
        .compact();
  }

  @Override
  public String getUserName(String token) {
    Claims claims = extractAllClaims(token);
    return claims.getSubject();
  }

  @Override
  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }
}
