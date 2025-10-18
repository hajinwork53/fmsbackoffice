package com.fms.backoffice.repository;

import com.fms.backoffice.domain.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    
    Page<Expense> findByUserId(Long userId, Pageable pageable);
    
    Page<Expense> findByBusinessNumber(String businessNumber, Pageable pageable);
    
    @Query("SELECT e FROM Expense e WHERE " +
           "(:companyName IS NULL OR e.companyName LIKE %:companyName%) AND " +
           "(:businessNumber IS NULL OR e.businessNumber = :businessNumber) AND " +
           "(:driverName IS NULL OR e.driverName LIKE %:driverName%) AND " +
           "(:vehicleRegistrationNumber IS NULL OR e.vehicleRegistrationNumber LIKE %:vehicleRegistrationNumber%) AND " +
           "(:expenseType IS NULL OR e.expenseType = :expenseType) AND " +
           "(:startDate IS NULL OR e.expenseDate >= :startDate) AND " +
           "(:endDate IS NULL OR e.expenseDate <= :endDate)")
    Page<Expense> searchExpenses(
            @Param("companyName") String companyName,
            @Param("businessNumber") String businessNumber,
            @Param("driverName") String driverName,
            @Param("vehicleRegistrationNumber") String vehicleRegistrationNumber,
            @Param("expenseType") Expense.ExpenseType expenseType,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            Pageable pageable);
}
