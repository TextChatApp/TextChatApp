package org.example.textChatApp.service;

import org.example.textChatApp.dto.ChatDetailsDTO;
import org.example.textChatApp.dto.PrivateMessageDTO;
import org.example.textChatApp.model.PrivateChat;
import org.example.textChatApp.model.PrivateMessage;
import org.example.textChatApp.model.User;
import org.example.textChatApp.repository.PrivateChatRepository;
import org.example.textChatApp.repository.PrivateMessageRepository;
import org.example.textChatApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrivateChatService {

    @Autowired
    private PrivateChatRepository privateChatRepository;
    @Autowired
    private PrivateMessageRepository privateMessageRepository;
    @Autowired
    private UserRepository userRepository;

    public PrivateMessage sendMessage(Long senderId, Long receiverId, String content) {
        // проверка существования пользователей
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        // проверка существования чата
        Optional<PrivateChat> existingChat = privateChatRepository.findChatByUsers(senderId, receiverId);

        PrivateChat chat = existingChat.orElseGet(() -> {
            // чата нет - создаем
            PrivateChat newChat = new PrivateChat();
            newChat.setUserOne(sender);
            newChat.setUserTwo(receiver);
            newChat.setCreatedAt(LocalDateTime.now());
            return privateChatRepository.save(newChat);
        });

        // создание и сохранение сообщения
        PrivateMessage message = new PrivateMessage();
        message.setChat(chat);
        message.setUser(sender);
        message.setContent(content);
        message.setCreatedAt(LocalDateTime.now());
        return privateMessageRepository.save(message);
    }

    // история сообщений
    public List<PrivateMessageDTO> getMessagesForChat(Long chatId) {
        List<PrivateMessage> messages = privateMessageRepository.findByChatIdOrderByCreatedAtAsc(chatId);
        return messages.stream()
                .map(PrivateMessageDTO::new) // сообщение в DTO
                .collect(Collectors.toList());
    }

    public Long startOrGetChat(Long currentUserId, Long userId) {
        // проверка чата
        Optional<PrivateChat> existingChat = privateChatRepository.findExistingChat(currentUserId, userId);

        if (existingChat.isPresent()) {
            return existingChat.get().getId(); // id существующего чата
        }

        //создание нового чата
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new RuntimeException("Current user not found"));
        User otherUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Recipient user not found"));

        PrivateChat newChat = new PrivateChat();
        newChat.setUserOne(currentUser);
        newChat.setUserTwo(otherUser);

        PrivateChat savedChat = privateChatRepository.save(newChat);
        return savedChat.getId(); // id нового чата
    }

    public ChatDetailsDTO getChatDetails(Long chatId) {
        // проверка существует ли чат
        PrivateChat chat = privateChatRepository.findById(chatId)
                .orElseThrow(() -> new RuntimeException("Chat not found"));

        // сообщения чата
        List<PrivateMessageDTO> messages = privateMessageRepository.findByChatIdOrderByCreatedAtAsc(chatId)
                .stream()
                .map(PrivateMessageDTO::new)
                .collect(Collectors.toList());

        // DTO с информацией о чате
        return new ChatDetailsDTO(chat, messages);
    }

    public List<PrivateChat> getAllChats() {
        return privateChatRepository.findAll();
    }
}
