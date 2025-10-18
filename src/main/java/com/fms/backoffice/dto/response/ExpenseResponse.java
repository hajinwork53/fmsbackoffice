package com.fms.backoffice.dto.response;

import com.fms.backoffice.domain.Expense;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseResponse {
    private List<Expense> content;
    private ExpenseSummary summary;
    private long totalElements;
    private int totalPages;
    private int currentPage;
}
