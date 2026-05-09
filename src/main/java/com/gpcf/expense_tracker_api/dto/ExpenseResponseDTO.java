package com.gpcf.expense_tracker_api.dto;

import com.gpcf.expense_tracker_api.enums.ExpenseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseResponseDTO {

    private Long id;

    private String username;

    private String name;

    private Double amount;

    private LocalDate expenseDate;

    private String description;

    private ExpenseStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
