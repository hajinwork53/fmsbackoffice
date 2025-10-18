package com.fms.backoffice.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "vehicle_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class VehicleInfo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id")
    private Long vehicleId;
    
    @Column(unique = true, nullable = false, length = 50)
    private String vin;
    
    @Column(name = "battery_capacity", precision = 10, scale = 2)
    private BigDecimal batteryCapacity;
    
    @Column(name = "vehicle_model", length = 100)
    private String vehicleModel;
    
    @Column(name = "model_year")
    private Integer modelYear;
    
    @Column(name = "initial_mileage", precision = 10, scale = 2)
    private BigDecimal initialMileage;
    
    @Column(name = "user_id")
    private Long userId;
    
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
