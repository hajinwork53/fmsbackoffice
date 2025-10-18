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
@Table(name = "driving_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class DrivingLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long logId;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "company_name", length = 200)
    private String companyName;
    
    @Column(name = "business_number", length = 12)
    private String businessNumber;
    
    @Column(name = "vehicle_type", length = 100)
    private String vehicleType;
    
    @Column(name = "vehicle_registration_number", length = 20)
    private String vehicleRegistrationNumber;
    
    @Column(name = "driving_date", nullable = false)
    private LocalDate drivingDate;
    
    @Column(length = 100)
    private String department;
    
    @Column(name = "driver_name", length = 100)
    private String driverName;
    
    @Column(name = "mileage_before", precision = 10, scale = 2)
    private BigDecimal mileageBefore;
    
    @Column(name = "mileage_after", precision = 10, scale = 2)
    private BigDecimal mileageAfter;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal distance;
    
    @Column(name = "commute_distance", precision = 10, scale = 2)
    private BigDecimal commuteDistance;
    
    @Column(name = "business_distance", precision = 10, scale = 2)
    private BigDecimal businessDistance;
    
    @Column(name = "total_distance", precision = 10, scale = 2)
    private BigDecimal totalDistance;
    
    @Column(name = "business_usage_distance", precision = 10, scale = 2)
    private BigDecimal businessUsageDistance;
    
    @Column(name = "business_usage_ratio", precision = 5, scale = 2)
    private BigDecimal businessUsageRatio;
    
    @Column(name = "vehicle_data_id")
    private Long vehicleDataId;
    
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
