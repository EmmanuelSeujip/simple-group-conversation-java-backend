package com.backend.messagerie.config;

import com.backend.messagerie.models.message.Message;
import com.backend.messagerie.models.message.MessageType;
import com.backend.messagerie.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Objects;

@Configuration
@RequiredArgsConstructor
public class WebSocketEventListener {
    private final UserService userService;
    private final SimpMessageSendingOperations simpMessageSendingOperations;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent sessionDisconnectEvent){
        var headerAccessor = StompHeaderAccessor.wrap(sessionDisconnectEvent.getMessage());
        var userName = Objects.requireNonNull(headerAccessor.getSessionAttributes()).get("username").toString();

        if (Objects.nonNull(userName)){
            var user = userService.findByUsername(userName);
            user.setOnline(false);

            var leaveMessage= new Message();
            leaveMessage.setType(MessageType.LEAVE);
            leaveMessage.setSender(user);
            simpMessageSendingOperations.convertAndSend("/topic/public",leaveMessage);
        }
    }
}
