package com.payflow.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileRequestDto {

    private Long authUserId;
    private String fullName;
    private String email;
    private String role;


}
