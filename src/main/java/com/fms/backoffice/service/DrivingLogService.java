package com.fms.backoffice.service;

import com.fms.backoffice.domain.DrivingLog;
import com.fms.backoffice.domain.User;
import com.fms.backoffice.dto.response.DrivingLogResponse;
import com.fms.backoffice.dto.response.DrivingLogSummary;
import com.fms.backoffice.exception.CustomException;
import com.fms.backoffice.repository.DrivingLogRepository;
import com.fms.backoffice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DrivingLogService {
    
    private final DrivingLogRepository drivingLogRepository;
    private final UserRepository userRepository;
    
    @Transactional(readOnly = true)
    public DrivingLogResponse getDrivingLogs(
            String username,
            String companyName,
            String userName,
            String vehicleType,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable) {
        
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException("AUTH-001", "사용자를 찾을 수 없습니다"));
        
        Page<DrivingLog> logs;
        
        if (currentUser.getUserType() == User.UserType.ADMIN) {
            logs = drivingLogRepository.searchDrivingLogs(
                    companyName, userName, vehicleType, startDate, endDate, pageable);
        } else {
            logs = drivingLogRepository.searchDrivingLogs(
                    currentUser.getCompanyName(), userName, vehicleType, startDate, endDate, pageable);
        }
        
        DrivingLogSummary summary = calculateSummary(logs.getContent());
        
        return DrivingLogResponse.builder()
                .content(logs.getContent())
                .summary(summary)
                .totalElements(logs.getTotalElements())
                .totalPages(logs.getTotalPages())
                .currentPage(logs.getNumber())
                .build();
    }
    
    private DrivingLogSummary calculateSummary(List<DrivingLog> logs) {
        BigDecimal totalDistance = logs.stream()
                .map(DrivingLog::getTotalDistance)
                .filter(d -> d != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal businessUsageDistance = logs.stream()
                .map(DrivingLog::getBusinessUsageDistance)
                .filter(d -> d != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal businessUsageRatio = totalDistance.compareTo(BigDecimal.ZERO) > 0
                ? businessUsageDistance.divide(totalDistance, 4, BigDecimal.ROUND_HALF_UP)
                        .multiply(new BigDecimal("100"))
                : BigDecimal.ZERO;
        
        return DrivingLogSummary.builder()
                .totalDistance(totalDistance)
                .businessUsageDistance(businessUsageDistance)
                .businessUsageRatio(businessUsageRatio)
                .build();
    }
}
