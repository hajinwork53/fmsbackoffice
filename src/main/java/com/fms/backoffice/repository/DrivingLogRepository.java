package com.fms.backoffice.repository;

import com.fms.backoffice.domain.DrivingLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface DrivingLogRepository extends JpaRepository<DrivingLog, Long> {
    
    Page<DrivingLog> findByUserId(Long userId, Pageable pageable);
    
    Page<DrivingLog> findByBusinessNumber(String businessNumber, Pageable pageable);
    
    @Query("SELECT d FROM DrivingLog d WHERE " +
           "(:companyName IS NULL OR d.companyName LIKE %:companyName%) AND " +
           "(:userName IS NULL OR d.driverName LIKE %:userName%) AND " +
           "(:vehicleType IS NULL OR d.vehicleType LIKE %:vehicleType%) AND " +
           "(:startDate IS NULL OR d.drivingDate >= :startDate) AND " +
           "(:endDate IS NULL OR d.drivingDate <= :endDate)")
    Page<DrivingLog> searchDrivingLogs(
            @Param("companyName") String companyName,
            @Param("userName") String userName,
            @Param("vehicleType") String vehicleType,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable);
}
