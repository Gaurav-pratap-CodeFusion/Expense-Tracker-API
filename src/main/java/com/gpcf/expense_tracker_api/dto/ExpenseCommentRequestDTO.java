package com.gpcf.expense_tracker_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseCommentRequestDTO {

    @NotBlank(message = "Comment message is required")
    @Size(max = 1000, message = "Comment cannot exceed 1000 characters")
    private String message;}
