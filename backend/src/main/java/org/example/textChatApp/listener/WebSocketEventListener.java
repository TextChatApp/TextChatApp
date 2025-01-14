package org.example.textChatApp.listener;

import org.example.textChatApp.model.User;
import org.example.textChatApp.repository.UserRepository;
import org.example.textChatApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Optional;

@Component
public class WebSocketEventListener {

    @Autowired
    private UserService userService;

    // обработчик события подключения
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String token = headerAccessor.getFirstNativeHeader("Authorization");
        if (token != null) {
            Long userId = userService.getUserIdFromToken(token.replace("Bearer ", ""));
            userService.updateStatus(userId, "online");
        }
    }

    // обработчик события отключения
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String token = headerAccessor.getFirstNativeHeader("Authorization");
        if (token != null) {
            Long userId = userService.getUserIdFromToken(token.replace("Bearer ", ""));
            userService.updateStatus(userId, "offline");
        }
    }
}