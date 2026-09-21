package com.backend.messagerie.controller.message;

import com.backend.messagerie.dto.message.MessageDto;
import com.backend.messagerie.mapper.UserMapper;
import com.backend.messagerie.models.User;
import com.backend.messagerie.models.message.Message;
import com.backend.messagerie.service.message.MessageService;
import com.backend.messagerie.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final MessageService messageService;

    @MessageMapping("/chat.sendingMessage")
    @SendTo("/topic/public")
    public MessageDto sendMessage(@Payload MessageDto message, Principal principal) throws IllegalAccessException {
        var user=userService.findByUsername(principal.getName());
        return MessageDto.builder()
                .content(message.content())
                .sentAt(message.sentAt())
                .type(message.type())
                .user(userMapper.cleanUserModel(user))
                .build();
    }

    @MessageMapping("/chat.typing")
    @SendTo("/topic/public")
    public MessageDto typing(@Payload MessageDto message, Principal principal) throws IllegalAccessException {
        var user=userService.findByUsername(principal.getName());
        return MessageDto.builder()
                .content(message.content())
                .sentAt(message.sentAt())
                .type(message.type())
                .user(userMapper.cleanUserModel(user))
                .build();
    }
}
