package com.gpcf.expense_tracker_api.service.imp;


import com.gpcf.expense_tracker_api.dto.ExpenseResponseDTO;
import com.gpcf.expense_tracker_api.dto.RejectExpenseRequestDTO;
import com.gpcf.expense_tracker_api.entity.Expense;
import com.gpcf.expense_tracker_api.entity.ExpenseComment;
import com.gpcf.expense_tracker_api.entity.User;
import com.gpcf.expense_tracker_api.enums.ExpenseStatus;
import com.gpcf.expense_tracker_api.enums.SenderType;
import com.gpcf.expense_tracker_api.exception.ResourceNotFoundException;
import com.gpcf.expense_tracker_api.repository.ExpenseCommentRepo;
import com.gpcf.expense_tracker_api.repository.ExpenseRepo;
import com.gpcf.expense_tracker_api.security.service.AuthenticatedUserService;
import com.gpcf.expense_tracker_api.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final ExpenseRepo expenseRepo;

    private final ExpenseCommentRepo expenseCommentRepo;

    private final AuthenticatedUserService authenticatedUserService;

    @Override
    public List<ExpenseResponseDTO> getAllExpenses() {

        List<Expense> expenses = expenseRepo.findAll();

        return expenses.stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public List<ExpenseResponseDTO> getPendingExpenses() {

        List<Expense> expenses =
                expenseRepo.findByStatus(ExpenseStatus.PENDING);

        return expenses.stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public void approveExpense(Long id) {

        Expense expense = expenseRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

        expense.setStatus(ExpenseStatus.APPROVED);

        expense.setUpdatedAt(LocalDateTime.now());

        expenseRepo.save(expense);
    }

    @Override
    public void rejectExpense(Long id, RejectExpenseRequestDTO dto) {

        User admin = authenticatedUserService.getLoggedInUser();

        Expense expense = expenseRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

        expense.setStatus(ExpenseStatus.REJECTED);

        expense.setUpdatedAt(LocalDateTime.now());

        expenseRepo.save(expense);

        ExpenseComment comment = new ExpenseComment();

        comment.setMessage(dto.getMessage());

        comment.setSenderType(SenderType.ADMIN);

        comment.setCreatedAt(LocalDateTime.now());

        comment.setExpense(expense);

        comment.setUser(admin);

        expenseCommentRepo.save(comment);
    }

    private ExpenseResponseDTO mapToDTO(Expense expense) {

        return new ExpenseResponseDTO(
                expense.getId(),
                expense.getName(),
                expense.getAmount(),
                expense.getExpenseDate(),
                expense.getDescription(),
                expense.getStatus(),
                expense.getCreatedAt(),
                expense.getUpdatedAt()
        );
    }
}
