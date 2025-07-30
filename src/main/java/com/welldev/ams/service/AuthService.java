package com.welldev.ams.service;

import org.springframework.http.ResponseEntity;

import com.welldev.ams.model.request.AuthResponse;
import com.welldev.ams.model.response.BaseResponse;

public interface AuthService {
  ResponseEntity<BaseResponse> signIn(String email, String password);
  AuthResponse logout();
}
