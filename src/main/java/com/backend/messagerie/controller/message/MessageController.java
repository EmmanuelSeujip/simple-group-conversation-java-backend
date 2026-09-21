package com.backend.messagerie.controller.message;

import com.backend.messagerie.dto.message.MessageDto;
import com.backend.messagerie.mapper.MessageMapper;
import com.backend.messagerie.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final MessageMapper messageMapper;

    @GetMapping("/search")
    public List<MessageDto> search(@RequestParam("q") String keyword) {
        return messageService.search(keyword)
                .stream()
                .map(messageMapper::cleanMessageModel)
                .toList();
    }
}
