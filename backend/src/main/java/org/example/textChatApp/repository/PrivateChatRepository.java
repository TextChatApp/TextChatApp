package org.example.textChatApp.repository;

import org.example.textChatApp.model.PrivateChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivateChatRepository extends JpaRepository<PrivateChat, Long> {
}
