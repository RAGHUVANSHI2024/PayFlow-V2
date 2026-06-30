package com.payflow.notification.entity;

import com.payflow.notification.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private Boolean isRead;

    @CreationTimestamp
    private LocalDateTime createdAt;
}