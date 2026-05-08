package com.gpcf.expense_tracker_api.service;


import com.gpcf.expense_tracker_api.dto.ExpenseResponseDTO;
import com.gpcf.expense_tracker_api.dto.RejectExpenseRequestDTO;

import java.util.List;

public interface AdminService {

    List<ExpenseResponseDTO> getAllExpenses();

    List<ExpenseResponseDTO> getPendingExpenses();

    void approveExpense(Long id);

    void rejectExpense(Long id, RejectExpenseRequestDTO dto);
}