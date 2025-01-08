package org.example.textChatApp.service;

import org.example.textChatApp.dto.UserDTO;
import org.example.textChatApp.model.User;
import org.example.textChatApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // регистрация
    public User register(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    // авторизация (проверка логина и пароля)
    public UserDTO login(User user) {
        User foundUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return new UserDTO(foundUser);  // User в UserDTO
    }

    public String generateToken(Long userId) {
        return "token-" + userId + "-" + System.currentTimeMillis();
    }

    public Long getUserIdFromToken(String token) {
        String[] parts = token.split("-");
        if (parts.length < 3) {
            throw new RuntimeException("Invalid token format");
        }
        return Long.parseLong(parts[1]);
    }
}
