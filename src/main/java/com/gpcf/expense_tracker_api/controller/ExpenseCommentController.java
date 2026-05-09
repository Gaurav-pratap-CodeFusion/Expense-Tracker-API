package com.gpcf.expense_tracker_api.controller;

import com.gpcf.expense_tracker_api.dto.ExpenseCommentRequestDTO;
import com.gpcf.expense_tracker_api.dto.ExpenseCommentResponseDTO;
import com.gpcf.expense_tracker_api.service.ExpenseCommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ExpenseCommentController {

    private final ExpenseCommentService expenseCommentService;

    @PostMapping("/{expenseId}/ ")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<String> addComment(@PathVariable Long expenseId, @Valid @RequestBody ExpenseCommentRequestDTO dto) {

        expenseCommentService.addComment(expenseId, dto);

        return ResponseEntity.ok("Comment added successfully");
    }

    @GetMapping("/{expenseId}/comments")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<ExpenseCommentResponseDTO>> getExpenseComments(@PathVariable Long expenseId) {

        return ResponseEntity.ok(expenseCommentService.getExpenseComments(expenseId)
        );
    }
}
