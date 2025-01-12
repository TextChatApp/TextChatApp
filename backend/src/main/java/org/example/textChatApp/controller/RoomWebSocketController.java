package org.example.textChatApp.controller;

import org.example.textChatApp.dto.RoomMessageDTO;
import org.example.textChatApp.model.Message;
import org.example.textChatApp.service.RoomChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class RoomWebSocketController {
    @Autowired
    private RoomChatService roomChatService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public RoomWebSocketController(RoomChatService roomChatService, SimpMessagingTemplate messagingTemplate) {
        this.roomChatService = roomChatService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/room/sendMessage")
    public void sendMessage(RoomMessageDTO messageDTO) {
        Message savedMessage = roomChatService.saveMessage(
                messageDTO.getRoomId(),
                messageDTO.getUserId(),
                messageDTO.getContent()
        );
        RoomMessageDTO response = new RoomMessageDTO(savedMessage);
        String topic = "/topic/room/" + response.getRoomId();
        messagingTemplate.convertAndSend(topic, response);
    }
}
