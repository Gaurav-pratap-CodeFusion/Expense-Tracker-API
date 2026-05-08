package com.gpcf.expense_tracker_api.service;

import com.gpcf.expense_tracker_api.dto.ExpenseCommentRequestDTO;
import com.gpcf.expense_tracker_api.dto.ExpenseCommentResponseDTO;

import java.util.List;

public interface ExpenseCommentService {

    void addComment(Long expenseId,
                    ExpenseCommentRequestDTO dto);

    List<ExpenseCommentResponseDTO> getExpenseComments(
            Long expenseId);
}