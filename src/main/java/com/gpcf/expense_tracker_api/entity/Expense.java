package com.gpcf.expense_tracker_api.entity;

import com.gpcf.expense_tracker_api.enums.ExpenseStatus;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Double amount;

    private LocalDate expenseDate;

    private String description;

    @Enumerated(EnumType.STRING)
    private ExpenseStatus status;

    @OneToMany(mappedBy = "expense", cascade = CascadeType.ALL)
    private List<ExpenseComment> expenseComments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}