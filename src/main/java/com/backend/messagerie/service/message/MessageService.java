package com.backend.messagerie.service.message;

import com.backend.messagerie.models.message.Message;
import com.backend.messagerie.models.message.MessageType;
import com.backend.messagerie.repository.MessageRepository;
import com.backend.messagerie.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserService userService;

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> getAllMessage() {
        return messageRepository.findAllByOrderByCreatedAtAsc();
    }

    public List<Message> getRecentMessage(String username) {
        return messageRepository.findTop50BySenderIdOrderByCreatedAtDesc(userService.getIdByUsername(username));
    }

    public List<Message> search(String keyword) {
        return messageRepository.searchByContentOrUsername(keyword.trim());
    }

    public List<Message> getLatestMessagePerUser() {
        return messageRepository.findLatestMessagePerUser(MessageType.CHAT);
    }
}
