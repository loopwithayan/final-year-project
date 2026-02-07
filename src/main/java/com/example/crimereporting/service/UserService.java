package com.example.crimereporting.service;

import com.example.crimereporting.model.User;
import java.util.Optional;

public interface UserService {
    Optional<User> findById(Long id);
}
