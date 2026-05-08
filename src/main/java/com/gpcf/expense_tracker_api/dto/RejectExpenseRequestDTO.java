package com.gpcf.expense_tracker_api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RejectExpenseRequestDTO {

    @NotBlank(message = "Reject reason is required")
    private String message;}
