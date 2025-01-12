package org.example.textChatApp.controller;

import org.example.textChatApp.dto.RoomDTO;
import org.example.textChatApp.dto.RoomMessageDTO;
import org.example.textChatApp.model.Message;
import org.example.textChatApp.model.Room;
import org.example.textChatApp.service.RoomChatService;
import org.example.textChatApp.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomChatService roomChatService;
    @Autowired
    private RoomService roomService;

    public RoomController(RoomChatService roomChatService, RoomService roomService) {
        this.roomChatService = roomChatService;
        this.roomService = roomService;
    }

    // сообщения комнаты
    @GetMapping("/{roomId}/messages")
    public List<RoomMessageDTO> getMessages(@PathVariable Long roomId) {
        List<Message> messages = roomChatService.getMessagesByRoomId(roomId);
        return messages.stream().map(RoomMessageDTO::new).collect(Collectors.toList());
    }

    // информация о комнате
    @GetMapping("/{roomId}")
    public RoomDTO getRoomInfo(@PathVariable Long roomId) {
        Room room = roomService.getRoomById(roomId);
        return new RoomDTO(room);
    }
}
