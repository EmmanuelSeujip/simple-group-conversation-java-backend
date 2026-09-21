package com.backend.messagerie.mapper;

import com.backend.messagerie.dto.user.UserDto;
import com.backend.messagerie.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto cleanUserModel(User user){
        return UserDto.builder()
                .username(user.getUsername())
                .avatarColor(user.getAvatarColor())
                .isOnline(user.isOnline())
                .lastSeen(user.getLastSeen())
                .build();
    }
}
