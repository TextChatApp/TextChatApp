package org.example.textChatApp.controller;

import org.example.textChatApp.model.PrivateChat;
import org.example.textChatApp.service.PrivateChatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class PrivateChatController {

    private final PrivateChatService privateChatService;

    public PrivateChatController(PrivateChatService privateChatService) {
        this.privateChatService = privateChatService;
    }

    @GetMapping
    public List<PrivateChat> getAllChats() {
        return privateChatService.getAllChats();
    }
}
