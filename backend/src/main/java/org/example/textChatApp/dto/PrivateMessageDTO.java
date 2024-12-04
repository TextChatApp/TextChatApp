package org.example.textChatApp.dto;

import org.example.textChatApp.model.PrivateMessage;

import java.time.LocalDateTime;

public class PrivateMessageDTO {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private UserDTO sender;
    private UserDTO receiver;

    public PrivateMessageDTO() {
    }

    public PrivateMessageDTO(PrivateMessage message) {
        this.id = message.getId();
        this.content = message.getContent();
        this.createdAt = message.getCreatedAt();
        this.sender = new UserDTO(message.getUser());
        this.receiver = new UserDTO(message.getChat().getUserTwo());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UserDTO getSender() {
        return sender;
    }

    public void setSender(UserDTO sender) {
        this.sender = sender;
    }

    public UserDTO getReceiver() {
        return receiver;
    }

    public void setReceiver(UserDTO receiver) {
        this.receiver = receiver;
    }
}
