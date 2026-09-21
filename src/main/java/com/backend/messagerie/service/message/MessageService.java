package com.backend.messagerie.service.message;

import com.backend.messagerie.models.message.Message;
import com.backend.messagerie.repository.MessageRepository;
import com.backend.messagerie.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    public final MessageRepository messageRepository;
    public final UserService userService;
    public Message saveMessage(Message message){
        return messageRepository.save(message);
    }
    public List<Message> getAllMessage(String username){
        return  messageRepository.findAllByOrderByCreatedAtAsc(userService.getIdByUsername(username));
    }
    public List<Message> getRecentMessage(String username){
        return messageRepository.findByTop50ByOrderByCreatedAtDesc(userService.getIdByUsername(username))
    }
}
