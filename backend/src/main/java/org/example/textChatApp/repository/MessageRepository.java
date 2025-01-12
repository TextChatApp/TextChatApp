package org.example.textChatApp.repository;

import org.example.textChatApp.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByRoomId(Long roomId);
}
