package com.payflow.user.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "User Id", example = "1")
    private Long id;

    @Schema(description = "Auth User Id", example = "1001")
    private Long authUserId;

    @Schema(description = "First Name", example = "Raghu")
    private String fullName;

    @Column(unique = true)
    @Schema(description = "Email", example = "raghu@gmail.com")
    private String email;

    @Schema(description = "Role", example = "USER")
    private String role;
}