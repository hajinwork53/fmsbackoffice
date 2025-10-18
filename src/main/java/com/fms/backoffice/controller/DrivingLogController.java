package com.fms.backoffice.controller;

import com.fms.backoffice.dto.response.ApiResponse;
import com.fms.backoffice.dto.response.DrivingLogResponse;
import com.fms.backoffice.service.DrivingLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/driving-logs")
@RequiredArgsConstructor
public class DrivingLogController {
    
    private final DrivingLogService drivingLogService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<DrivingLogResponse>> getDrivingLogs(
            @RequestParam(required = false) String companyName,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String vehicleType,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            Authentication authentication) {
        
        String username = authentication.getName();
        Pageable pageable = PageRequest.of(page, size);
        
        DrivingLogResponse response = drivingLogService.getDrivingLogs(
                username, companyName, userName, vehicleType, 
                startDate, endDate, pageable);
        
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportDrivingLogs(
            @RequestParam(required = false) String companyName,
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String vehicleType,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Authentication authentication) {
        
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=driving_logs.xlsx")
                .header("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .body(new byte[0]);
    }
}
