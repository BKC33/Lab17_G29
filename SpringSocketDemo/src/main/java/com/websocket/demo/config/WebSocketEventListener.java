package com.websocket.demo.config;

import com.websocket.demo.chat.ChatMessage;
import com.websocket.demo.chat.MessageType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {
    private final SimpMessageSendingOperations messageSendingOperations;
    private int onlineUsers = 0;



    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        onlineUsers++;
        updateOnlineUsers();
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            var chatMessage = ChatMessage.builder()
                    .type(MessageType.LEAVE)
                    .sender(username)
                    .content("has left the chat")
                    .build();
            messageSendingOperations.convertAndSend("/topic/public", chatMessage);
            onlineUsers--;
            updateOnlineUsers();
        }
    }
    private void updateOnlineUsers() {
        ChatMessage chatMessage = ChatMessage.builder()
                .type(MessageType.ONLINE_COUNT)
                .onlineUsers(onlineUsers)
                .build();
        messageSendingOperations.convertAndSend("/topic/public", chatMessage);
    }
}
