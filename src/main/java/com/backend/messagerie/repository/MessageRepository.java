package com.backend.messagerie.repository;

import com.backend.messagerie.models.message.Message;
import com.backend.messagerie.models.message.MessageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findAllBySenderIdOrderByCreatedAtAsc(Long senderId);

    List<Message> findTop50BySenderIdOrderByCreatedAtDesc(Long senderId);

    @Query("""
            SELECT m FROM Message m
            JOIN m.sender s
            WHERE LOWER(m.content) LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR LOWER(s.username) LIKE LOWER(CONCAT('%', :keyword, '%'))
            ORDER BY m.sentAt DESC
            """)
    List<Message> searchByContentOrUsername(@Param("keyword") String keyword);

    @Query("""
            SELECT m FROM Message m
            WHERE m.type = :type
              AND m.sentAt = (
                  SELECT MAX(m2.sentAt) FROM Message m2
                  WHERE m2.sender = m.sender AND m2.type = :type
              )
            ORDER BY m.sentAt DESC
            """)
    List<Message> findLatestMessagePerUser(@Param("type") MessageType type);
}
