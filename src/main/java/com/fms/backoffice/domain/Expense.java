package com.fms.backoffice.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "expenses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Expense {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "expense_id")
    private Long expenseId;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "company_name", length = 200)
    private String companyName;
    
    @Column(name = "business_number", length = 12)
    private String businessNumber;
    
    @Column(name = "driver_name", length = 100)
    private String driverName;
    
    @Column(name = "vehicle_registration_number", length = 20)
    private String vehicleRegistrationNumber;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "expense_type", nullable = false, length = 20)
    private ExpenseType expenseType;
    
    @Column(name = "expense_date", nullable = false)
    private LocalDate expenseDate;
    
    @Column(name = "store_name", length = 200)
    private String storeName;
    
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;
    
    @Column(name = "detail_content", columnDefinition = "TEXT")
    private String detailContent;
    
    @Column(name = "charging_kwh", precision = 10, scale = 2)
    private BigDecimal chargingKwh;
    
    @Column(length = 20)
    private String phone;
    
    @Column(name = "card_name", length = 100)
    private String cardName;
    
    @Column(columnDefinition = "TEXT")
    private String memo;
    
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    public enum ExpenseType {
        REPAIR, CHARGING, OTHER
    }
}
