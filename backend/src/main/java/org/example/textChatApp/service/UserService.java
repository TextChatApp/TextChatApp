package org.example.textChatApp.service;

import org.example.textChatApp.dto.UserDTO;
import org.example.textChatApp.model.User;
import org.example.textChatApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser(Long userId, String newUsername, String newEmail, String newPassword) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // имя
        if (newUsername != null && !newUsername.isEmpty()) {
            if (userRepository.existsByUsername(newUsername)) {
                throw new RuntimeException("Username already exists");
            }
            existingUser.setUsername(newUsername);
        }

        // почта
        if (newEmail != null && !newEmail.isEmpty()) {
            if (userRepository.existsByEmail(newEmail)) {
                throw new RuntimeException("Email already exists");
            }
            existingUser.setEmail(newEmail);
        }

        // пароль
        if (newPassword != null && !newPassword.isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(newPassword));
        }

        existingUser.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(existingUser);
    }

    @Value("${file.upload-dir}")
    private String uploadDir;
    public User uploadAvatar(Long userId, MultipartFile file) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // директория существует
        File uploadDirectory = new File(uploadDir);
        if (!uploadDirectory.exists()) {
            uploadDirectory.mkdirs();
        }

        try {
            // уникальное имя для файла
            String fileName = "avatar-" + userId + "-" + System.currentTimeMillis() +
                    "." + getFileExtension(file.getOriginalFilename());
            String filePath = Paths.get(fileName).toString();

            // сохраняем файл
            Files.copy(file.getInputStream(), Paths.get(uploadDir, fileName));

            // обновляем пользователя
            user.setAvatar(filePath);
            userRepository.save(user);

            return user;
        } catch (IOException e) {
            throw new RuntimeException("Error while uploading avatar", e);
        }
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
