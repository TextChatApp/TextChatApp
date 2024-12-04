package org.example.textChatApp.dto;

import org.example.textChatApp.model.PrivateChat;

import java.util.List;

public class ChatDetailsDTO {
    private Long chatId;
    private UserDTO userOne;
    private UserDTO userTwo;
    private List<PrivateMessageDTO> messages;

    public ChatDetailsDTO() {
    }

    public ChatDetailsDTO(PrivateChat chat, List<PrivateMessageDTO> messages) {
        this.chatId = chat.getId();
        this.userOne = new UserDTO(chat.getUserOne());
        this.userTwo = new UserDTO(chat.getUserTwo());
        this.messages = messages;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public UserDTO getUserOne() {
        return userOne;
    }

    public void setUserOne(UserDTO userOne) {
        this.userOne = userOne;
    }

    public UserDTO getUserTwo() {
        return userTwo;
    }

    public void setUserTwo(UserDTO userTwo) {
        this.userTwo = userTwo;
    }

    public List<PrivateMessageDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<PrivateMessageDTO> messages) {
        this.messages = messages;
    }
}
