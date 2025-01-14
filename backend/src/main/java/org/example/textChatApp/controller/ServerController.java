package org.example.textChatApp.controller;

import org.example.textChatApp.dto.RoomDTO;
import org.example.textChatApp.dto.UserDTO;
import org.example.textChatApp.dto.UserForServerDTO;
import org.example.textChatApp.model.Server;
import org.example.textChatApp.model.ServerMember;
import org.example.textChatApp.model.Room;
import org.example.textChatApp.model.User;
import org.example.textChatApp.repository.UserRepository;
import org.example.textChatApp.service.ServerService;
import org.example.textChatApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.ServerResponse;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/servers")
public class ServerController {

    @Autowired
    private ServerService serverService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createServer(@RequestHeader("Authorization") String token,
                                          @RequestBody Map<String, String> request) {
        try {
            // id пользователя из токена
            Long userId = userService.getUserIdFromToken(token);
            User currentUser = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            String serverName = request.get("name");
            String description = request.get("description");

            // создание сервера
            Server server = serverService.createServer(serverName, description, currentUser);

            return ResponseEntity.ok(Map.of(
                    "serverId", server.getId(),
                    "message", "Server created successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/my-servers")
    public ResponseEntity<?> getMyServers(@RequestHeader("Authorization") String token) {
        try {
            Long userId = userService.getUserIdFromToken(token);

            // сервера, на которые подписан пользователь
            List<Server> servers = serverService.getServersByUserId(userId);

            List<Object> response = servers.stream().map(server -> {
                // список комнат
                List<RoomDTO> rooms = serverService.getRoomsByServerAndUser(server.getId(), userId)
                        .stream()
                        .map(RoomDTO::new)
                        .collect(Collectors.toList());

                return new ServerResponse(
                        server.getId(),
                        server.getName(),
                        server.getDescription(),
                        server.getInviteCode(),
                        server.getCreatedAt(),
                        rooms
                );
            }).collect(Collectors.toList());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    private record ServerResponse(Long id, String name, String description, String inviteCode,
                                  java.time.LocalDateTime createdAt, List<RoomDTO> rooms) {
    }

    @PostMapping("/join")
    public ResponseEntity<?> joinServer(@RequestHeader("Authorization") String token,
                                        @RequestBody Map<String, String> request) {
        try {
            // id пользователя из токена
            Long userId = userService.getUserIdFromToken(token);

            User currentUser = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // invite_code из запроса
            String inviteCode = request.get("inviteCode");

            // присоединение пользователя к серверу
            ServerMember serverMember = serverService.addUserToServer(inviteCode, currentUser);

            return ResponseEntity.ok(Map.of(
                    "serverId", serverMember.getServer().getId(),
                    "message", "Joined server successfully",
                    "role", serverMember.getRole()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{serverId}/rooms")
    public ResponseEntity<?> getServerRooms(@RequestHeader("Authorization") String token,
                                            @PathVariable Long serverId) {
        try {
            Long userId = userService.getUserIdFromToken(token);

            // список комнат сервера
            List<Room> rooms = serverService.getRoomsByServerAndUser(serverId, userId);

            List<RoomDTO> response = rooms.stream()
                    .map(RoomDTO::new)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(403).body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }

    @GetMapping("/{serverId}")
    public ResponseEntity<?> getServerById(@RequestHeader("Authorization") String token,
                                           @PathVariable Long serverId) {
        try {
            Long userId = userService.getUserIdFromToken(token);
            // является ли пользователь участником сервера
            Server server = serverService.getServerByIdAndUser(serverId, userId);
            // комнаты
            List<Room> rooms = serverService.getRoomsByServerAndUser(serverId, userId);

            // ответ про сервер
            return ResponseEntity.ok(Map.of(
                    "id", server.getId(),
                    "name", server.getName(),
                    "description", server.getDescription(),
                    "ownerId", server.getOwner().getId(),
                    "inviteCode", server.getInviteCode(),
                    "createdAt", server.getCreatedAt(),
                    "rooms", rooms.stream().map(RoomDTO::new).collect(Collectors.toList())
            ));
        } catch (Exception e) {
            return ResponseEntity.status(403).body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }

    @GetMapping("/{serverId}/users")
    public ResponseEntity<?> getUsersByServerId(@RequestHeader("Authorization") String token,
                                                @PathVariable Long serverId) {
        try {
            Long userId = userService.getUserIdFromToken(token);
            // список пользователей сервера
            List<UserForServerDTO> users = serverService.getUsersByServerId(serverId, userId);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(403).body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }

    @DeleteMapping("/{serverId}/users/{userId}")
    public ResponseEntity<?> removeUserFromServer(@RequestHeader("Authorization") String token,
                                                  @PathVariable Long serverId,
                                                  @PathVariable Long userId) {
        try {
            Long adminId = userService.getUserIdFromToken(token);
            // удаление пользователя с сервера
            serverService.removeUserFromServer(serverId, userId, adminId);
            return ResponseEntity.ok(Map.of(
                    "message", "User removed successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(403).body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }

    @PatchMapping("/{serverId}/users/{userId}/role")
    public ResponseEntity<?> changeUserRole(@RequestHeader("Authorization") String token,
                                            @PathVariable Long serverId,
                                            @PathVariable Long userId,
                                            @RequestBody Map<String, String> request) {
        try {
            Long adminId = userService.getUserIdFromToken(token);
            String newRole = request.get("role");
            // изменение роли пользователя
            serverService.changeUserRole(serverId, userId, adminId, newRole);
            return ResponseEntity.ok(Map.of(
                    "message", "User role updated successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(403).body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }

    @DeleteMapping("/{serverId}/leave")
    public ResponseEntity<?> leaveServer(@RequestHeader("Authorization") String token,
                                         @PathVariable Long serverId) {
        try {
            Long userId = userService.getUserIdFromToken(token);
            // выход пользователя из сервера
            serverService.leaveServer(serverId, userId);
            return ResponseEntity.ok(Map.of(
                    "message", "Successfully left the server"
            ));
        } catch (Exception e) {
            return ResponseEntity.status(403).body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }

    @PostMapping("/{serverId}/rooms")
    public ResponseEntity<?> addRoom(@RequestHeader("Authorization") String token,
                                     @PathVariable Long serverId,
                                     @RequestBody Map<String, Object> request) {
        try {
            Long adminId = userService.getUserIdFromToken(token);
            String roomName = (String) request.get("name");
            boolean isPrivate = (boolean) request.getOrDefault("isPrivate", false);

            Room room = serverService.addRoom(serverId, adminId, roomName, isPrivate);

            return ResponseEntity.ok(Map.of(
                    "roomId", room.getId(),
                    "message", "Room added successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/rooms/{roomId}")
    public ResponseEntity<?> deleteRoom(@RequestHeader("Authorization") String token,
                                        @PathVariable Long roomId) {
        try {
            Long adminId = userService.getUserIdFromToken(token);
            serverService.deleteRoom(roomId, adminId);
            return ResponseEntity.ok(Map.of("message", "Room deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PatchMapping("/rooms/{roomId}")
    public ResponseEntity<?> updateRoom(@RequestHeader("Authorization") String token,
                                        @PathVariable Long roomId,
                                        @RequestBody Map<String, Object> request) {
        try {
            Long adminId = userService.getUserIdFromToken(token);
            String newName = (String) request.get("name");
            Boolean isPrivate = (Boolean) request.get("isPrivate");

            Room room = serverService.updateRoom(roomId, adminId, newName, isPrivate);

            return ResponseEntity.ok(Map.of(
                    "roomId", room.getId(),
                    "message", "Room updated successfully"
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

}
