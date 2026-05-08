package com.gpcf.expense_tracker_api.service.imp;

import com.gpcf.expense_tracker_api.dto.ExpenseCommentRequestDTO;
import com.gpcf.expense_tracker_api.dto.ExpenseCommentResponseDTO;
import com.gpcf.expense_tracker_api.entity.Expense;
import com.gpcf.expense_tracker_api.entity.ExpenseComment;
import com.gpcf.expense_tracker_api.entity.User;
import com.gpcf.expense_tracker_api.enums.SenderType;
import com.gpcf.expense_tracker_api.exception.ResourceNotFoundException;
import com.gpcf.expense_tracker_api.repository.ExpenseCommentRepo;
import com.gpcf.expense_tracker_api.repository.ExpenseRepo;
import com.gpcf.expense_tracker_api.security.service.AuthenticatedUserService;
import com.gpcf.expense_tracker_api.service.ExpenseCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseCommentServiceImpl
        implements ExpenseCommentService {

    private final ExpenseRepo expenseRepo;

    private final ExpenseCommentRepo expenseCommentRepo;

    private final AuthenticatedUserService authenticatedUserService;

    @Override
    public void addComment(Long expenseId, ExpenseCommentRequestDTO dto) {

        User loggedInUser = authenticatedUserService.getLoggedInUser();

        Expense expense = expenseRepo.findById(expenseId)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found"));

        boolean isOwner = expense.getUser().getId().equals(loggedInUser.getId());

        boolean isAdmin =
                loggedInUser.getRoles()
                        .stream()
                        .anyMatch(role ->
                                role.getRoleName()
                                        .name()
                                        .equals("ADMIN"));

        if (!isOwner && !isAdmin) {
            throw new RuntimeException("You are not authorized to comment");
        }

        ExpenseComment comment = new ExpenseComment();

        comment.setMessage(dto.getMessage());

        if (isAdmin) {
            comment.setSenderType(SenderType.ADMIN);
        } else {
            comment.setSenderType(SenderType.USER);
        }

        comment.setCreatedAt(LocalDateTime.now());

        comment.setExpense(expense);

        comment.setUser(loggedInUser);

        expenseCommentRepo.save(comment);
    }

    @Override
    public List<ExpenseCommentResponseDTO>
    getExpenseComments(Long expenseId) {

        User loggedInUser = authenticatedUserService.getLoggedInUser();

        Expense expense = expenseRepo.findById(expenseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Expense not found"));

        boolean isOwner = expense.getUser().getId().equals(loggedInUser.getId());

        boolean isAdmin = loggedInUser.getRoles()
                .stream()
                .anyMatch(role ->
                        role.getRoleName()
                                .name()
                                .equals("ADMIN"));

        if (!isOwner && !isAdmin) {
            throw new RuntimeException("You are not authorized to view comments");
        }

        List<ExpenseComment> comments = expenseCommentRepo.findByExpense(expense);

        return comments.stream()
                .map(this::mapToDTO)
                .toList();
    }

    private ExpenseCommentResponseDTO
    mapToDTO(ExpenseComment comment) {

        return new ExpenseCommentResponseDTO(
                comment.getId(),
                comment.getMessage(),
                comment.getSenderType(),
                comment.getUser().getUsername(),
                comment.getCreatedAt()
        );
    }
}
