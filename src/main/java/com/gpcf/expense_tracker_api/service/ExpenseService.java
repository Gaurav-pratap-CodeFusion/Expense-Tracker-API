package com.gpcf.expense_tracker_api.service;

import com.gpcf.expense_tracker_api.dto.ExpenseRequestDTO;
import com.gpcf.expense_tracker_api.dto.ExpenseResponseDTO;

import java.util.List;

public interface ExpenseService {

    ExpenseResponseDTO addExpense(ExpenseRequestDTO dto);

    List<ExpenseResponseDTO> getMyExpenses();

    ExpenseResponseDTO getExpenseById(Long id);

    ExpenseResponseDTO updateExpense(Long id, ExpenseRequestDTO dto);

    void deleteExpense(Long id);
}
