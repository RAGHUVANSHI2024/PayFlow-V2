package com.payflow.audit.entity;

import com.payflow.audit.enums.AuditEventType;
import jakarta.persistence.*;
import lombok.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuditLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String eventId;

    @Enumerated(EnumType.STRING)
    private AuditEventType eventType;

    private String serviceName;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    private LocalDateTime createdAt;

}
