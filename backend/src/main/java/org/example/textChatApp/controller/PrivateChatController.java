package org.example.textChatApp.controller;

import org.example.textChatApp.dto.ChatDetailsDTO;
import org.example.textChatApp.dto.ChatRequestDTO;
import org.example.textChatApp.dto.PrivateMessageDTO;
import org.example.textChatApp.model.PrivateChat;
import org.example.textChatApp.model.PrivateMessage;
import org.example.textChatApp.service.PrivateChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class PrivateChatController {

    @Autowired
    private PrivateChatService privateChatService;

    public PrivateChatController(PrivateChatService privateChatService) {
        this.privateChatService = privateChatService;
    }

    @GetMapping
    public List<PrivateChat> getAllChats() {
        return privateChatService.getAllChats();
    }

    @PostMapping("/{receiverId}/messages")
    public ResponseEntity<?> sendMessage(@RequestParam Long senderId,
                                         @PathVariable Long receiverId,
                                         @RequestBody String content) {
        try {
            PrivateMessage message = privateChatService.sendMessage(senderId, receiverId, content);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{chatId}/messages")
    public ResponseEntity<List<PrivateMessageDTO>> getMessages(@PathVariable Long chatId) {
        List<PrivateMessageDTO> messages = privateChatService.getMessagesForChat(chatId);
        return ResponseEntity.ok(messages);
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
    public ResponseEntity<?> getChatById(@PathVariable Long chatId) {
        try {
            // Получение данных чата через сервис
            ChatDetailsDTO chatDetails = privateChatService.getChatDetails(chatId);
            return ResponseEntity.ok(chatDetails);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
