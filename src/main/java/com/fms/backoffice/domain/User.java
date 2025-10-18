package com.fms.backoffice.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    
    @Column(unique = true, nullable = false, length = 50)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false, length = 20)
    private UserType userType;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(length = 100)
    private String department;
    
    @Column(nullable = false, length = 20)
    private String phone;
    
    @Column(nullable = false, length = 100)
    private String email;
    
    @Column(name = "company_name", length = 200)
    private String companyName;
    
    @Column(name = "business_number", length = 12)
    private String businessNumber;
    
    @Column(name = "vehicle_registration_number", length = 20)
    private String vehicleRegistrationNumber;
    
    @Column(name = "ad_consent")
    private Boolean adConsent = false;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "approval_status", length = 20)
    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING;
    
    @Column(name = "approved_by")
    private Long approvedBy;
    
    @Column(name = "approved_at")
    private LocalDateTime approvedAt;
    
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public enum UserType {
        ADMIN, USER
    }
    
    public enum ApprovalStatus {
        PENDING, APPROVED, REJECTED
    }
}
