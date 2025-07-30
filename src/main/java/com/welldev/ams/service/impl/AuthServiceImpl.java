package com.welldev.ams.service.impl;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.welldev.ams.model.db.Users;
import com.welldev.ams.model.request.AuthResponse;
import com.welldev.ams.model.response.BaseResponse;
import com.welldev.ams.repositories.UserRepository;
import com.welldev.ams.service.AuthService;
import com.welldev.ams.service.JWTService;
import com.welldev.ams.utils.Utils;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final JWTService jwtService;
  private final Utils utils;

  public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, JWTService jwtService, Utils utils) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.jwtService = jwtService;
    this.utils = utils;
  }

  @Override
  public ResponseEntity<BaseResponse> signIn(String email, String password) {
    // This will authenticate the user, or throw an exception if credentials are bad
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(email, password)
      );
      AuthResponse ar = new AuthResponse();
      Optional<Users> user = userRepository.findByEmail(email);
      final String jwt = jwtService.generateToken(user.get());
      final String refreshJwt = jwtService.generateRefreshToken(user.get());
      ar.setToken(jwt);
      ar.setRefreshToken(refreshJwt);
      return ResponseEntity.ok(utils.generateResponse(ar, true, HttpStatus.OK.value(), "Sign In Successful"));
    }
    catch (Exception e) {
      log.error("Sign in failed {}", e);return ResponseEntity.internalServerError().body(utils.generateResponse(null, false, HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
  }

  @Override
  public AuthResponse logout() {
    return null;
  }
}
