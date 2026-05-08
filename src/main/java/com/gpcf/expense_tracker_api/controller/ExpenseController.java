package com.gpcf.expense_tracker_api.controller;

import com.gpcf.expense_tracker_api.dto.ExpenseRequestDTO;
import com.gpcf.expense_tracker_api.dto.ExpenseResponseDTO;
import com.gpcf.expense_tracker_api.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ExpenseResponseDTO> addExpense(@Valid @RequestBody ExpenseRequestDTO dto) {

        return new ResponseEntity<>(expenseService.addExpense(dto), HttpStatus.CREATED);
    }

    @GetMapping("/my-expenses")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ExpenseResponseDTO>> getMyExpenses() {

        return ResponseEntity.ok(expenseService.getMyExpenses());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ExpenseResponseDTO> getExpenseById(@PathVariable Long id) {

        return ResponseEntity.ok(expenseService.getExpenseById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ExpenseResponseDTO> updateExpense(@PathVariable Long id, @Valid @RequestBody ExpenseRequestDTO dto) {

        return ResponseEntity.ok(expenseService.updateExpense(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> deleteExpense(@PathVariable Long id) {

        expenseService.deleteExpense(id);

        return ResponseEntity.ok("Expense deleted successfully");
    }
}