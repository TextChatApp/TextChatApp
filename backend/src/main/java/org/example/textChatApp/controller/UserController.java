package org.example.textChatApp.controller;

import org.example.textChatApp.model.User;
import org.example.textChatApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Получение всех пользователей
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/me")
    public User getMyInfo(@RequestHeader("Authorization") String token) {
        Long userId = userService.getUserIdFromToken(token.replace("Bearer ", ""));
        return userService.getUserById(userId);
    }

    @PatchMapping("/me")
    public ResponseEntity<?> updateMyInfo(@RequestHeader("Authorization") String token,
                                          @RequestBody Map<String, Object> updates) {
        try {
            Long userId = userService.getUserIdFromToken(token.replace("Bearer ", ""));
            String newUsername = (String) updates.get("username");
            String newEmail = (String) updates.get("email");
            String newPassword = (String) updates.get("password");

            User updatedUser = userService.updateUser(userId, newUsername, newEmail, newPassword);

            return ResponseEntity.ok(Map.of(
                    "user", updatedUser
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/me/avatar")
    public User uploadAvatar(@RequestHeader("Authorization") String token,
                             @RequestParam("file") MultipartFile file) {
        Long userId = userService.getUserIdFromToken(token.replace("Bearer ", ""));
        return userService.uploadAvatar(userId, file);
    }
}
