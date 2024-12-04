package org.example.textChatApp.repository;

import org.example.textChatApp.model.PrivateMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrivateMessageRepository extends JpaRepository<PrivateMessage, Long> {
    // поиск сообщений для чата
    List<PrivateMessage> findByChatIdOrderByCreatedAtAsc(Long chatId);
}
