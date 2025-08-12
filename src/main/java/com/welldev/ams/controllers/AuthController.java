package com.welldev.ams.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.welldev.ams.model.request.AuthResponse;
import com.welldev.ams.model.request.LogInDTO;
import com.welldev.ams.model.response.BaseResponse;
import com.welldev.ams.service.AuthService;
import com.welldev.ams.utils.Utils;

@RestController
@RequestMapping("/v1")
public class AuthController {
  private final AuthService authService;
  private final Utils utils;

  public AuthController(AuthService authService, Utils utils) {
    this.authService = authService;
    this.utils = utils;
  }

  @PostMapping("/login")
  public ResponseEntity<BaseResponse> createAuthenticationToken(@RequestBody LogInDTO logInDTO) {
    AuthResponse ar = authService.signIn(logInDTO.getEmail(), logInDTO.getPassword());
    return ResponseEntity.ok(utils.generateResponse(ar,true, HttpStatus.OK.value(), "Sign in successful"));
  }

  @GetMapping("/logout")
  public ResponseEntity<BaseResponse> logout() {
    authService.logout();
    return ResponseEntity.noContent().build();
  }

}
