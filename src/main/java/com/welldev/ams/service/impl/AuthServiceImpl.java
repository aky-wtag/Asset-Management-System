package com.welldev.ams.service.impl;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.welldev.ams.model.db.Users;
import com.welldev.ams.model.request.AuthResponse;
import com.welldev.ams.repositories.UserRepository;
import com.welldev.ams.service.AuthService;
import com.welldev.ams.service.JWTService;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final JWTService jwtService;

  public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, JWTService jwtService) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.jwtService = jwtService;
  }

  @Override
  public AuthResponse signIn(String email, String password) {
    // This will authenticate the user, or throw an exception if credentials are bad
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(email, password)
      );
      AuthResponse ar = new AuthResponse();
      Optional<Users> user = userRepository.findByEmail(email);
      final String jwt = jwtService.generateToken(user.get());
      ar.setToken(jwt);
      return ar;
    }
    catch (Exception e) {
      log.error("Sign in failed {}", e);
      return new AuthResponse();
    }
  }
}
