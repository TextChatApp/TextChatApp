package org.example.textChatApp.service;

import org.example.textChatApp.model.PrivateChat;
import org.example.textChatApp.repository.PrivateChatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivateChatService {

    private final PrivateChatRepository privateChatRepository;

    public PrivateChatService(PrivateChatRepository privateChatRepository) {
        this.privateChatRepository = privateChatRepository;
    }

    public List<PrivateChat> getAllChats() {
        return privateChatRepository.findAll();
    }
}
