package org.example.textChatApp.repository;

import org.example.textChatApp.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByServerId(Long serverId);
}
