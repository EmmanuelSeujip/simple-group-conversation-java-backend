package com.backend.messagerie.controller.message;

import com.backend.messagerie.dto.message.MessageDto;
import com.backend.messagerie.mapper.MessageMapper;
import com.backend.messagerie.mapper.UserMapper;
import com.backend.messagerie.models.message.Message;
import com.backend.messagerie.models.message.MessageType;
import com.backend.messagerie.service.message.MessageService;
import com.backend.messagerie.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final MessageMapper messageMapper;
    private final MessageService messageService;
    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/chat.sendingMessage")
    @SendTo("/topic/public")
    public MessageDto sendMessage(@Payload MessageDto message, Principal principal) {
        var user = userService.findByUsername(principal.getName());

        Message entity = new Message();
        entity.setContent(message.content());
        entity.setSentAt(message.sentAt() != null ? message.sentAt() : LocalDateTime.now());
        entity.setType(message.type() != null ? message.type() : MessageType.CHAT);
        entity.setSender(user);
        Message saved = messageService.saveMessage(entity);

        messagingTemplate.convertAndSend("/topic/latest-messages", latestMessagePerUser());

        return messageMapper.cleanMessageModel(saved);
    }

    @MessageMapping("/chat.typing")
    @SendTo("/topic/public")
    public MessageDto typing(@Payload MessageDto message, Principal principal) {
        var user = userService.findByUsername(principal.getName());
        return MessageDto.builder()
                .content(message.content())
                .sentAt(message.sentAt())
                .type(message.type())
                .user(userMapper.cleanUserModel(user))
                .build();
    }

    @MessageMapping("/chat.latestPerUser")
    @SendTo("/topic/latest-messages")
    public List<MessageDto> latestMessagePerUser() {
        return messageService.getLatestMessagePerUser()
                .stream()
                .map(messageMapper::cleanMessageModel)
                .toList();
    }
}
