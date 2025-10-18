package com.fms.backoffice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long userId;
    private String username;
    private String name;
    private String userType;
    private String companyName;
    private String businessNumber;
    private String department;
    private String phone;
    private String email;
    private String approvalStatus;
    private LocalDateTime createdAt;
}
