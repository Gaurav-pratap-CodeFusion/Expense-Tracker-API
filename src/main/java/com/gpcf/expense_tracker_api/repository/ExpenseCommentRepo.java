package com.gpcf.expense_tracker_api.repository;

import com.gpcf.expense_tracker_api.entity.Expense;
import com.gpcf.expense_tracker_api.entity.ExpenseComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseCommentRepo extends JpaRepository<ExpenseComment,Long> {
    List<ExpenseComment> findByExpense(Expense expense);
}
