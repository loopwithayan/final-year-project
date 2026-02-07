package com.example.crimereporting.service;

import com.example.crimereporting.dto.AuthResponse;
import com.example.crimereporting.dto.LoginRequest;
import com.example.crimereporting.dto.RegisterRequest;

public interface AuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
