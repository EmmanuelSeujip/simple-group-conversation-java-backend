package com.backend.messagerie.mapper;

import com.backend.messagerie.dto.message.MessageDto;
import com.backend.messagerie.models.message.Message;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class MessageMapper {

    private final UserMapper userMapper;
    public MessageDto cleanMessageModel(Message message){
        return MessageDto.builder()
                .content(message.getContent())
                .sentAt(message.getSentAt())
                .type(message.getType())
                .user(userMapper.cleanUserModel(message.getSender()))
                .build();
    }
}
