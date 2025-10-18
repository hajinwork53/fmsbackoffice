package com.fms.backoffice.controller;

import com.fms.backoffice.domain.Expense;
import com.fms.backoffice.dto.response.ApiResponse;
import com.fms.backoffice.dto.response.ExpenseResponse;
import com.fms.backoffice.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/expenses")
@RequiredArgsConstructor
public class ExpenseController {
    
    private final ExpenseService expenseService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<ExpenseResponse>> getExpenses(
            @RequestParam(required = false) String companyName,
            @RequestParam(required = false) String businessNumber,
            @RequestParam(required = false) String driverName,
            @RequestParam(required = false) String vehicleRegistrationNumber,
            @RequestParam(required = false, defaultValue = "ALL") String expenseType,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size,
            Authentication authentication) {
        
        String username = authentication.getName();
        Pageable pageable = PageRequest.of(page, size);
        
        ExpenseResponse response = expenseService.getExpenses(
                username, companyName, businessNumber, driverName,
                vehicleRegistrationNumber, expenseType, startDate, endDate, pageable);
        
        return ResponseEntity.ok(ApiResponse.success(response));
    }
    
    @GetMapping("/export")
    public ResponseEntity<byte[]> exportExpenses(
            @RequestParam(required = false) String companyName,
            @RequestParam(required = false) String businessNumber,
            @RequestParam(required = false) String driverName,
            @RequestParam(required = false) String vehicleRegistrationNumber,
            @RequestParam(required = false, defaultValue = "ALL") String expenseType,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Authentication authentication) {
        
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=expenses.xlsx")
                .header("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                .body(new byte[0]);
    }
    
    @PutMapping("/admin/{expenseId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Expense>> updateExpense(
            @PathVariable Long expenseId,
            @RequestBody Expense expense) {
        
        Expense updated = expenseService.updateExpense(expenseId, expense);
        return ResponseEntity.ok(ApiResponse.success(updated, "차계부가 수정되었습니다"));
    }
    
    @DeleteMapping("/admin/{expenseId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteExpense(@PathVariable Long expenseId) {
        expenseService.deleteExpense(expenseId);
        return ResponseEntity.ok(ApiResponse.success(null, "차계부가 삭제되었습니다"));
    }
}
