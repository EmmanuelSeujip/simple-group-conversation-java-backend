package com.backend.messagerie.dto.user;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserDto(
        String username,
        String avatarColor,
        LocalDateTime lastSeen,
        boolean isOnline
) {
}
