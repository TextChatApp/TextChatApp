package org.example.textChatApp.service;

import org.example.textChatApp.model.Message;
import org.example.textChatApp.model.Room;
import org.example.textChatApp.model.User;
import org.example.textChatApp.repository.MessageRepository;
import org.example.textChatApp.repository.RoomRepository;
import org.example.textChatApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoomChatService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    public Message saveMessage(Long roomId, Long userId, String content) {
        // существование комнаты и пользователя
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Message message = new Message();
        message.setRoom(room);
        message.setUser(user);
        message.setContent(content);
        message.setCreatedAt(LocalDateTime.now());

        return messageRepository.save(message);
    }

    public List<Message> getMessagesByRoomId(Long roomId) {
        return messageRepository.findByRoomId(roomId);
    }
}
