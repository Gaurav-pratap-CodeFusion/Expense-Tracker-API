package com.gpcf.expense_tracker_api.dto;

import com.gpcf.expense_tracker_api.enums.SenderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseCommentResponseDTO {

    private Long id;

    private String message;

    private SenderType senderType;

    private String username;

    private LocalDateTime createdAt;
}
