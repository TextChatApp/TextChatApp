package org.example.textChatApp.listener;

import org.example.textChatApp.dto.UserDTO;
import org.example.textChatApp.model.User;
import org.example.textChatApp.repository.UserRepository;
import org.example.textChatApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketEventListener {

    @Autowired
    private UserService userService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // хранилище токенов по идентификатору сессии
    private final ConcurrentHashMap<String, String> sessionTokenMap = new ConcurrentHashMap<>();

    // обработчик подключения
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        String token = headerAccessor.getFirstNativeHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.replace("Bearer ", "");
            sessionTokenMap.put(sessionId, token); // сохранение токена по сессии

            Long userId = userService.getUserIdFromToken(token);
            User user = userService.updateStatus(userId, "online");

            UserDTO userDTO = new UserDTO(user);
            messagingTemplate.convertAndSend("/topic/status", user);
        }
    }

    // обработчик отключения
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();

        String token = sessionTokenMap.remove(sessionId); // удаление токена по сессии
        if (token != null) {
            Long userId = userService.getUserIdFromToken(token);
            User user = userService.updateStatus(userId, "offline");

            UserDTO userDTO = new UserDTO(user);
            messagingTemplate.convertAndSend("/topic/status", user);
        }
    }
}