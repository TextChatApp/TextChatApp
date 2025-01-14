package org.example.textChatApp.dto;

import org.example.textChatApp.model.Message;

import java.time.LocalDateTime;

public class RoomMessageDTO {
    private Long roomId;
    private Long userId;
    private String content;
    private LocalDateTime createdAt;

    public RoomMessageDTO() {
    }

    public RoomMessageDTO(Message message) {
        this.roomId = message.getRoom().getId();
        this.userId = message.getUser();
        this.content = message.getContent();
        this.createdAt = message.getCreatedAt();
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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
}
