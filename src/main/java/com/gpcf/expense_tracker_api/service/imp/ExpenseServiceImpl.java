package com.gpcf.expense_tracker_api.service.imp;

import com.gpcf.expense_tracker_api.dto.ExpenseRequestDTO;
import com.gpcf.expense_tracker_api.dto.ExpenseResponseDTO;
import com.gpcf.expense_tracker_api.entity.Expense;
import com.gpcf.expense_tracker_api.entity.User;
import com.gpcf.expense_tracker_api.enums.ExpenseStatus;
import com.gpcf.expense_tracker_api.exception.ResourceNotFoundException;
import com.gpcf.expense_tracker_api.repository.ExpenseRepo;
import com.gpcf.expense_tracker_api.security.service.AuthenticatedUserService;
import com.gpcf.expense_tracker_api.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepo expenseRepo;
    private final AuthenticatedUserService authenticatedUserService;

    @Override
    public ExpenseResponseDTO addExpense(ExpenseRequestDTO dto) {

        User loggedInUser = authenticatedUserService.getLoggedInUser();

        Expense expense = new Expense();

        expense.setName(dto.getName());
        expense.setAmount(dto.getAmount());
        expense.setExpenseDate(dto.getExpenseDate());
        expense.setDescription(dto.getDescription());
        expense.setStatus(ExpenseStatus.PENDING);
        expense.setCreatedAt(LocalDateTime.now());
        expense.setUpdatedAt(LocalDateTime.now());
        expense.setUser(loggedInUser);

        Expense savedExpense = expenseRepo.save(expense);

        return mapToDTO(savedExpense);
    }

    @Override
    public List<ExpenseResponseDTO> getMyExpenses() {

        User loggedInUser = authenticatedUserService.getLoggedInUser();

        List<Expense> expenses = expenseRepo.findByUser(loggedInUser);

        return expenses.stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public ExpenseResponseDTO getExpenseById(Long id) {

        User loggedInUser = authenticatedUserService.getLoggedInUser();

        Expense expense = expenseRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

        if (!expense.getUser().getId().equals(loggedInUser.getId())) {
            throw new RuntimeException("You are not authorized to access this expense");
        }

        return mapToDTO(expense);
    }

    @Override
    public ExpenseResponseDTO updateExpense(Long id, ExpenseRequestDTO dto) {

        User loggedInUser = authenticatedUserService.getLoggedInUser();

        Expense expense = expenseRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Expense not found"));

        if (!expense.getUser().getId().equals(loggedInUser.getId())) {
            throw new RuntimeException("You are not authorized to update this expense");
        }

        expense.setName(dto.getName());
        expense.setAmount(dto.getAmount());
        expense.setExpenseDate(dto.getExpenseDate());
        expense.setDescription(dto.getDescription());

        expense.setUpdatedAt(LocalDateTime.now());

        if (expense.getStatus() == ExpenseStatus.REJECTED) {
            expense.setStatus(ExpenseStatus.PENDING);
        }

        Expense updatedExpense = expenseRepo.save(expense);

        return mapToDTO(updatedExpense);
    }

    @Override
    public void deleteExpense(Long id) {

        User loggedInUser = authenticatedUserService.getLoggedInUser();

        Expense expense = expenseRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Expense not found"));

        if (!expense.getUser().getId().equals(loggedInUser.getId())) {
            throw new RuntimeException("You are not authorized to delete this expense");
        }

        expenseRepo.delete(expense);
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