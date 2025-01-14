package org.example.textChatApp.controller;

import org.example.textChatApp.dto.PrivateMessageDTO;
import org.example.textChatApp.model.PrivateMessage;
import org.example.textChatApp.service.PrivateChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketChatController {

    @Autowired
    private PrivateChatService privateChatService;

    public WebSocketChatController(PrivateChatService privateChatService) {
        this.privateChatService = privateChatService;
    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public PrivateMessageDTO sendMessage(PrivateMessageDTO messageDTO) {
        PrivateMessage savedMessage = privateChatService.sendMessage(
                messageDTO.getSender().getId(),
                messageDTO.getReceiver().getId(),
                messageDTO.getContent()
        );
        return new PrivateMessageDTO(savedMessage);
    }
}
