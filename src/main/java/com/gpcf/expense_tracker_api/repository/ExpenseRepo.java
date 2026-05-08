package com.gpcf.expense_tracker_api.repository;

import com.gpcf.expense_tracker_api.entity.Expense;
import com.gpcf.expense_tracker_api.entity.User;
import com.gpcf.expense_tracker_api.enums.ExpenseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepo extends JpaRepository<Expense, Long> {
    List<Expense> findByUser(User user);
    List<Expense> findByStatus(ExpenseStatus status);
}
