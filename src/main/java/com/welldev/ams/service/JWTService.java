package com.welldev.ams.service;

import java.util.Date;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import io.jsonwebtoken.Claims;

import com.welldev.ams.model.db.Users;

public interface JWTService {
  String extractUsername(String token);
  Date extractExpiration(String token);
  <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
  Claims extractAllClaims(String token);
  Boolean isTokenExpired(String token);
  String generateToken(Users userDetails);
  Boolean validateToken(String token, UserDetails userDetails);
  String getUserName(String token);
}
