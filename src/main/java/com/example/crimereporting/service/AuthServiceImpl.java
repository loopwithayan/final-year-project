package com.example.crimereporting.service;

import com.example.crimereporting.dto.AuthResponse;
import com.example.crimereporting.dto.LoginRequest;
import com.example.crimereporting.dto.RegisterRequest;
import com.example.crimereporting.model.User;
import com.example.crimereporting.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        AuthenticationManager authenticationManager
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }

        User user = new User(
            request.name(),
            request.email(),
            passwordEncoder.encode(request.password()),
            request.role()
        );
        userRepository.save(user);
        return new AuthResponse(true, "Registration successful", user.getRole().name());
    }

    @Override
    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );
            String role = userRepository.findByEmail(request.email())
                .map(user -> user.getRole().name())
                .orElse("UNKNOWN");
            return new AuthResponse(true, "Login successful", role);
        } catch (Exception ex) {
            return new AuthResponse(false, "Invalid email or password", null);
        }
    }
}
