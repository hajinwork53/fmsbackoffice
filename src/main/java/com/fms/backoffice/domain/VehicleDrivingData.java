package com.fms.backoffice.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;

@Entity
@Table(name = "vehicle_driving_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class VehicleDrivingData {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "data_id")
    private Long dataId;
    
    @Column(name = "vehicle_id", nullable = false)
    private Long vehicleId;
    
    @Column(nullable = false, length = 50)
    private String vin;
    
    @Column(name = "driving_date", nullable = false)
    private LocalDate drivingDate;
    
    @Column(name = "driving_time", nullable = false)
    private LocalTime drivingTime;
    
    @Column(precision = 5, scale = 2)
    private BigDecimal soc;
    
    @Column(precision = 5, scale = 2)
    private BigDecimal soh;
    
    @Column(name = "power_consumption", precision = 10, scale = 2)
    private BigDecimal powerConsumption;
    
    @Column(name = "driving_time_after_start")
    private Integer drivingTimeAfterStart;
    
    @Column(name = "distance_after_start", precision = 10, scale = 2)
    private BigDecimal distanceAfterStart;
    
    @Column(name = "idle_time")
    private Integer idleTime;
    
    @Column(name = "rapid_accel_decel_count")
    private Integer rapidAccelDecelCount;
    
    @Column(name = "driving_score")
    private Integer drivingScore;
    
    @Column(name = "overspeed_120_count")
    private Integer overspeed120Count;
    
    @Column(name = "low_pressure_warning")
    private Boolean lowPressureWarning;
    
    @Column(name = "detachment_detected")
    private Boolean detachmentDetected;
    
    @Column(name = "fault_diagnosis", length = 500)
    private String faultDiagnosis;
    
    @Column(length = 50)
    private String weather;
    
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
