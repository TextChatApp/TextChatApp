package org.example.textChatApp.service;

import org.example.textChatApp.dto.UserDTO;
import org.example.textChatApp.dto.UserForServerDTO;
import org.example.textChatApp.model.Server;
import org.example.textChatApp.model.ServerMember;
import org.example.textChatApp.model.Room;
import org.example.textChatApp.model.User;
import org.example.textChatApp.repository.ServerRepository;
import org.example.textChatApp.repository.ServerMemberRepository;
import org.example.textChatApp.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ServerService {

    @Autowired
    private ServerRepository serverRepository;

    @Autowired
    private ServerMemberRepository serverMemberRepository;

    @Autowired
    private RoomRepository roomRepository;

    public Server createServer(String name, String description, User owner) {
        // создание сервер
        Server server = new Server();
        server.setName(name);
        server.setDescription(description);
        server.setOwner(owner);
        server.setInviteCode(UUID.randomUUID().toString()); // код приглашения
        server.setCreatedAt(LocalDateTime.now());
        server = serverRepository.save(server);

        // комната
        Room room = new Room();
        room.setName("General"); // название комнаты
        room.setServer(server);
        room.setPrivate(false); // публичная комната
        room.setCreatedAt(LocalDateTime.now());
        roomRepository.save(room);

        // создатель с ролью админ
        ServerMember serverMember = new ServerMember();
        serverMember.setServer(server);
        serverMember.setUser(owner);
        serverMember.setRole("admin");
        serverMember.setJoinedAt(LocalDateTime.now());
        serverMemberRepository.save(serverMember);

        return server;
    }

    public List<Server> getServersByUserId(Long userId) {
        // список серверов, на которые подписан пользователь
        return serverMemberRepository.findByUserId(userId)
                .stream()
                .map(serverMember -> serverMember.getServer())
                .collect(Collectors.toList());
    }

    public ServerMember addUserToServer(String inviteCode, User user) {
        // сервер по invite_code
        Server server = serverRepository.findByInviteCode(inviteCode)
                .orElseThrow(() -> new RuntimeException("Invalid invite code"));

        // проверка, что пользователь еще не присоединился
        boolean alreadyMember = serverMemberRepository.findByServerAndUser(server, user).isPresent();
        if (alreadyMember) {
            throw new RuntimeException("User is already a member of the server");
        }

        ServerMember serverMember = new ServerMember();
        serverMember.setServer(server);
        serverMember.setUser(user);
        serverMember.setRole("member"); // роль после присоединения
        serverMember.setJoinedAt(LocalDateTime.now());

        return serverMemberRepository.save(serverMember);
    }

    public List<Room> getRoomsByServerAndUser(Long serverId, Long userId) {
        // подписан ли пользователь на сервер
        boolean isMember = serverMemberRepository.findByServerIdAndUserId(serverId, userId).isPresent();
        if (!isMember) {
            throw new RuntimeException("User is not a member of this server");
        }
        return roomRepository.findByServerId(serverId);
    }

    public Server getServerByIdAndUser(Long serverId, Long userId) {
        // является ли пользователь участником сервера
        boolean isMember = serverMemberRepository.findByServerIdAndUserId(serverId, userId).isPresent();
        if (!isMember) {
            throw new RuntimeException("User is not a member of this server");
        }
        return serverRepository.findById(serverId)
                .orElseThrow(() -> new RuntimeException("Server not found"));
    }

    public List<UserForServerDTO> getUsersByServerId(Long serverId, Long userId) {
        // является ли пользователь участником сервера
        boolean isMember = serverMemberRepository.findByServerIdAndUserId(serverId, userId).isPresent();
        if (!isMember) {
            throw new RuntimeException("User is not a member of this server");
        }
        return serverMemberRepository.findByServerId(serverId).stream()
                .map(member -> new UserForServerDTO(member.getUser(), member.getRole()))
                .collect(Collectors.toList());
    }

    public void removeUserFromServer(Long serverId, Long targetUserId, Long adminId) {
        ServerMember adminMember = serverMemberRepository.findByServerIdAndUserId(serverId, adminId)
                .orElseThrow(() -> new RuntimeException("Admin is not a member of the server"));
        if (!"admin".equals(adminMember.getRole())) {
            throw new RuntimeException("Only admins can remove users");
        }
        ServerMember targetMember = serverMemberRepository.findByServerIdAndUserId(serverId, targetUserId)
                .orElseThrow(() -> new RuntimeException("User is not a member of the server"));
        serverMemberRepository.delete(targetMember);
    }

    public void changeUserRole(Long serverId, Long targetUserId, Long adminId, String newRole) {
        ServerMember adminMember = serverMemberRepository.findByServerIdAndUserId(serverId, adminId)
                .orElseThrow(() -> new RuntimeException("Admin is not a member of the server"));
        if (!"admin".equals(adminMember.getRole())) {
            throw new RuntimeException("Only admins can change roles");
        }
        ServerMember targetMember = serverMemberRepository.findByServerIdAndUserId(serverId, targetUserId)
                .orElseThrow(() -> new RuntimeException("User is not a member of the server"));
        targetMember.setRole(newRole);
        serverMemberRepository.save(targetMember);
    }

    public void leaveServer(Long serverId, Long userId) {
        Server server = serverRepository.findById(serverId)
                .orElseThrow(() -> new RuntimeException("Server not found"));
        // чтоб админ не вышел
        if (server.getOwner().getId().equals(userId)) {
            throw new RuntimeException("The owner cannot leave the server.");
        }
        // пользователь является участником сервера
        ServerMember serverMember = serverMemberRepository.findByServerIdAndUserId(serverId, userId)
                .orElseThrow(() -> new RuntimeException("User is not a member of this server"));
        serverMemberRepository.delete(serverMember);
    }

    public Room addRoom(Long serverId, Long adminId, String roomName, boolean isPrivate) {
        ServerMember adminMember = serverMemberRepository.findByServerIdAndUserId(serverId, adminId)
                .orElseThrow(() -> new RuntimeException("Admin is not a member of the server"));
        if (!"admin".equals(adminMember.getRole())) {
            throw new RuntimeException("Only admins can add rooms");
        }
        if (roomRepository.findByServerId(serverId).stream().anyMatch(room -> room.getName().equals(roomName))) {
            throw new RuntimeException("Room name must be unique");
        }

        Room room = new Room();
        room.setName(roomName);
        room.setPrivate(isPrivate);
        room.setServer(adminMember.getServer());
        room.setCreatedAt(LocalDateTime.now());

        return roomRepository.save(room);
    }

    public void deleteRoom(Long roomId, Long adminId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        Long serverId = room.getServer().getId();

        ServerMember adminMember = serverMemberRepository.findByServerIdAndUserId(serverId, adminId)
                .orElseThrow(() -> new RuntimeException("Admin is not a member of the server"));
        if (!"admin".equals(adminMember.getRole())) {
            throw new RuntimeException("Only admins can delete rooms");
        }

        roomRepository.delete(room);
    }

    public Room updateRoom(Long roomId, Long adminId, String newName, Boolean isPrivate) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        Long serverId = room.getServer().getId();

        ServerMember adminMember = serverMemberRepository.findByServerIdAndUserId(serverId, adminId)
                .orElseThrow(() -> new RuntimeException("Admin is not a member of the server"));
        if (!"admin".equals(adminMember.getRole())) {
            throw new RuntimeException("Only admins can update rooms");
        }

        if (newName != null && !newName.equals(room.getName())) {
            if (roomRepository.findByServerId(serverId).stream().anyMatch(r -> r.getName().equals(newName))) {
                throw new RuntimeException("Room name must be unique");
            }
            room.setName(newName);
        }

        if (isPrivate != null) {
            room.setPrivate(isPrivate);
        }

        return roomRepository.save(room);
    }

}
