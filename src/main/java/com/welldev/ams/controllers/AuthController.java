package com.welldev.ams.controllers;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.welldev.ams.model.request.AuthResponse;
import com.welldev.ams.model.request.LogInDTO;
import com.welldev.ams.service.AuthService;

@RestController
public class AuthController {
  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/login")
  public AuthResponse createAuthenticationToken(@RequestBody LogInDTO logInDTO) throws Exception {
    return authService.signIn(logInDTO.getEmail(), logInDTO.getPassword());
  }

}
