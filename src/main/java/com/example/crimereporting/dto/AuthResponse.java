package com.example.crimereporting.dto;

public record AuthResponse(
    boolean success,
    String message,
    String role
) {
}
