package com.backend.messagerie.models.message;

import com.backend.messagerie.models.BaseEntity;
import com.backend.messagerie.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Message extends BaseEntity {
    @Column(columnDefinition ="Text")
    private String content;
    private LocalDateTime sentAt;
    private MessageType type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_id")
    @JsonIgnore
    private User sender;

}
