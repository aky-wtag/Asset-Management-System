package com.welldev.ams.service;

import com.welldev.ams.model.request.AuthResponse;

public interface AuthService {
  AuthResponse signIn(String email, String password);
}
