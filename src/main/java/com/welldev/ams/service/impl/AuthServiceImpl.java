package com.welldev.ams.service.impl;

import java.util.Optional;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.welldev.ams.model.db.Users;
import com.welldev.ams.model.request.AuthResponse;
import com.welldev.ams.repositories.UserRepository;
import com.welldev.ams.service.AuthService;
import com.welldev.ams.service.JWTService;

@Slf4j
@Service
@Transactional
public class AuthServiceImpl implements AuthService {
  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final JWTService jwtService;
  private final RedisTemplate<String, Object> redisTemplate;

  public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
      JWTService jwtService, RedisTemplate<String, Object> redisTemplate)
  {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.jwtService = jwtService;
    this.redisTemplate = redisTemplate;
  }

  @Override
  public AuthResponse signIn(String email, String password) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    AuthResponse ar = new AuthResponse();
    Optional<Users> user = userRepository.findByEmail(email);
    final String jwt = jwtService.generateToken(user.get());
    final String refreshJwt = jwtService.generateRefreshToken(user.get());
    ar.setToken(jwt);
    ar.setRefreshToken(refreshJwt);
    redisTemplate.opsForValue().set(user.get().getEmail(), ar);
    return ar;
  }

  @Override
  public void logout() {
    String username = getCurrentUsername();
    if (username != null) {
      redisTemplate.delete(username);
      SecurityContextHolder.clearContext();
    }
  }

  private String getCurrentUsername() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.isAuthenticated()) {
      return authentication.getName();
    }
    return null;
  }
}
