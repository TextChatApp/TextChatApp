package org.example.textChatApp.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.textChatApp.dto.ChatDetailsDTO;
import org.example.textChatApp.dto.ChatRequestDTO;
import org.example.textChatApp.dto.PrivateMessageDTO;
import org.example.textChatApp.model.PrivateChat;
import org.example.textChatApp.model.PrivateMessage;
import org.example.textChatApp.service.PrivateChatService;
import org.example.textChatApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class PrivateChatController {

    @Autowired
    private PrivateChatService privateChatService;

    @Autowired
    private UserService userService;

    @GetMapping("/{chatId}/messages")
    public ResponseEntity<?> getMessages(@PathVariable Long chatId, HttpServletRequest request) {
        try {
            Long userId = UserIdFromToken(request);
            privateChatService.validateUserAccessToChat(chatId, userId); // проверка доступа
            List<PrivateMessageDTO> messages = privateChatService.getMessagesForChat(chatId);
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            return ResponseEntity.status(403).body("Access denied: " + e.getMessage());
        }
    }

    // кнопка "начать общение"
    @PostMapping("/start-chat")
    public ResponseEntity<Long> startChat(@RequestBody ChatRequestDTO request) {
        try {
            Long chatId = privateChatService.startOrGetChat(request.getCurrentUserId(), request.getUserId());
            return ResponseEntity.ok(chatId);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    // информация о чате по id
    @GetMapping("/{chatId}")
    public ResponseEntity<?> getChatById(@PathVariable Long chatId, HttpServletRequest request) {
        try {
            Long userId = UserIdFromToken(request);
            privateChatService.validateUserAccessToChat(chatId, userId); // проверка доступа
            ChatDetailsDTO chatDetails = privateChatService.getChatDetails(chatId);
            return ResponseEntity.ok(chatDetails);
        } catch (Exception e) {
            return ResponseEntity.status(403).body("Access denied: " + e.getMessage());
        }
    }

    @GetMapping("/my-chats")
    public ResponseEntity<?> getMyChats(HttpServletRequest request) {
        try {
            // id из токена
            Long userId = UserIdFromToken(request);
            // чаты пользователя
            List<PrivateChat> chats = privateChatService.getChatsByUserId(userId);
            return ResponseEntity.ok(chats);
        } catch (Exception e) {
            return ResponseEntity.status(403).body("Access denied: " + e.getMessage());
        }
    }

    // извлечение userId из токена
    private Long UserIdFromToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("token-")) {
            throw new RuntimeException("Invalid token");
        }
        return userService.getUserIdFromToken(token);
    }
}
