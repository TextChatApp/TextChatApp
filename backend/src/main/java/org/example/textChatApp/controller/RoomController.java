package org.example.textChatApp.controller;

import org.example.textChatApp.dto.RoomDTO;
import org.example.textChatApp.dto.RoomMessageDTO;
import org.example.textChatApp.model.Message;
import org.example.textChatApp.model.Room;
import org.example.textChatApp.repository.ServerMemberRepository;
import org.example.textChatApp.service.RoomChatService;
import org.example.textChatApp.service.RoomService;
import org.example.textChatApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomChatService roomChatService;
    @Autowired
    private RoomService roomService;
    @Autowired
    private UserService userService;
    @Autowired
    private ServerMemberRepository serverMemberRepository;

    // информация о комнате
    @GetMapping("/{roomId}")
    public ResponseEntity<?> getRoomInfoWithMessages(@RequestHeader("Authorization") String token,
                                                     @PathVariable Long roomId) {
        try {
            Long userId = userService.getUserIdFromToken(token);
            Room room = roomService.getRoomById(roomId);
            Long serverId = room.getServer().getId();

            boolean isMember = serverMemberRepository.findByServerIdAndUserId(serverId, userId).isPresent();
            if (!isMember) {
                return ResponseEntity.status(403).body(Map.of("error",
                                        "User is not a member of this server"));
            }

            List<Message> messages = roomChatService.getMessagesByRoomId(roomId);
            List<RoomMessageDTO> messageDTOs = messages.stream()
                    .map(RoomMessageDTO::new)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(Map.of(
                    "room", new RoomDTO(room),
                    "messages", messageDTOs
            ));

        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}
