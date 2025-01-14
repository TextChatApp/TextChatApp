package org.example.textChatApp.repository;

import org.example.textChatApp.model.Server;
import org.example.textChatApp.model.ServerMember;
import org.example.textChatApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServerMemberRepository extends JpaRepository<ServerMember, Long> {
    List<ServerMember> findByUserId(Long userId);

    Optional<ServerMember> findByServerAndUser(Server server, User user);

    Optional<ServerMember> findByServerIdAndUserId(Long serverId, Long userId);

    List<ServerMember> findByServerId(Long serverId);
}
