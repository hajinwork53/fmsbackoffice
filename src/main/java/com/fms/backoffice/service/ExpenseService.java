package com.fms.backoffice.service;

import com.fms.backoffice.domain.Expense;
import com.fms.backoffice.domain.User;
import com.fms.backoffice.dto.response.ExpenseResponse;
import com.fms.backoffice.dto.response.ExpenseSummary;
import com.fms.backoffice.exception.CustomException;
import com.fms.backoffice.repository.ExpenseRepository;
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
public class ExpenseService {
    
    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    
    @Transactional(readOnly = true)
    public ExpenseResponse getExpenses(
            String username,
            String companyName,
            String businessNumber,
            String driverName,
            String vehicleRegistrationNumber,
            String expenseType,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable) {
        
        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException("AUTH-001", "사용자를 찾을 수 없습니다"));
        
        Expense.ExpenseType type = null;
        if (expenseType != null && !expenseType.equals("ALL")) {
            type = Expense.ExpenseType.valueOf(expenseType);
        }
        
        Page<Expense> expenses;
        
        if (currentUser.getUserType() == User.UserType.ADMIN) {
            expenses = expenseRepository.searchExpenses(
                    companyName, businessNumber, driverName, 
                    vehicleRegistrationNumber, type, startDate, endDate, pageable);
        } else {
            expenses = expenseRepository.searchExpenses(
                    currentUser.getCompanyName(), currentUser.getBusinessNumber(), 
                    driverName, vehicleRegistrationNumber, type, startDate, endDate, pageable);
        }
        
        ExpenseSummary summary = calculateSummary(expenses.getContent());
        
        return ExpenseResponse.builder()
                .content(expenses.getContent())
                .summary(summary)
                .totalElements(expenses.getTotalElements())
                .totalPages(expenses.getTotalPages())
                .currentPage(expenses.getNumber())
                .build();
    }
    
    @Transactional
    public Expense updateExpense(Long expenseId, Expense expense) {
        Expense existing = expenseRepository.findById(expenseId)
                .orElseThrow(() -> new CustomException("DATA-001", "차계부 데이터를 찾을 수 없습니다"));
        
        existing.setCompanyName(expense.getCompanyName());
        existing.setBusinessNumber(expense.getBusinessNumber());
        existing.setDriverName(expense.getDriverName());
        existing.setVehicleRegistrationNumber(expense.getVehicleRegistrationNumber());
        existing.setExpenseType(expense.getExpenseType());
        existing.setExpenseDate(expense.getExpenseDate());
        existing.setStoreName(expense.getStoreName());
        existing.setAmount(expense.getAmount());
        existing.setDetailContent(expense.getDetailContent());
        existing.setChargingKwh(expense.getChargingKwh());
        existing.setPhone(expense.getPhone());
        existing.setCardName(expense.getCardName());
        existing.setMemo(expense.getMemo());
        
        return expenseRepository.save(existing);
    }
    
    @Transactional
    public void deleteExpense(Long expenseId) {
        if (!expenseRepository.existsById(expenseId)) {
            throw new CustomException("DATA-001", "차계부 데이터를 찾을 수 없습니다");
        }
        expenseRepository.deleteById(expenseId);
    }
    
    private ExpenseSummary calculateSummary(List<Expense> expenses) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal repairAmount = BigDecimal.ZERO;
        BigDecimal chargingAmount = BigDecimal.ZERO;
        BigDecimal otherAmount = BigDecimal.ZERO;
        
        for (Expense expense : expenses) {
            totalAmount = totalAmount.add(expense.getAmount());
            
            switch (expense.getExpenseType()) {
                case REPAIR:
                    repairAmount = repairAmount.add(expense.getAmount());
                    break;
                case CHARGING:
                    chargingAmount = chargingAmount.add(expense.getAmount());
                    break;
                case OTHER:
                    otherAmount = otherAmount.add(expense.getAmount());
                    break;
            }
        }
        
        return ExpenseSummary.builder()
                .totalAmount(totalAmount)
                .repairAmount(repairAmount)
                .chargingAmount(chargingAmount)
                .otherAmount(otherAmount)
                .build();
    }
}
