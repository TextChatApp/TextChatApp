package org.example.textChatApp.repository;

import org.example.textChatApp.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServerRepository extends JpaRepository<Server, Long> {
    Optional<Server> findByInviteCode(String inviteCode);
}
