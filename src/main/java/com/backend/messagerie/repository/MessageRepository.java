package com.backend.messagerie.repository;

import com.backend.messagerie.models.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {
    List<Message> findByTop50ByOrderByCreatedAtDesc(Long id);
    List<Message> findAllByOrderByCreatedAtAsc(Long id);
}
