package com.backend.messagerie.dto.message;

import com.backend.messagerie.dto.user.UserDto;
import com.backend.messagerie.models.message.MessageType;
import lombok.Builder;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
public record MessageDto(
        String content,
        LocalDateTime sentAt,
        MessageType type,
        UserDto user
) {
}
