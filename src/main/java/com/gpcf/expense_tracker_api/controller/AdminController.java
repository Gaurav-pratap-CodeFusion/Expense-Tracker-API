package com.gpcf.expense_tracker_api.controller;

import com.gpcf.expense_tracker_api.dto.ExpenseResponseDTO;
import com.gpcf.expense_tracker_api.dto.RejectExpenseRequestDTO;
import com.gpcf.expense_tracker_api.entity.Role;
import com.gpcf.expense_tracker_api.entity.User;
import com.gpcf.expense_tracker_api.enums.AppRole;
import com.gpcf.expense_tracker_api.exception.ResourceNotFoundException;
import com.gpcf.expense_tracker_api.repository.RoleRepo;
import com.gpcf.expense_tracker_api.repository.UserRepo;
import com.gpcf.expense_tracker_api.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private UserRepo userRepository;
    @Autowired
    private RoleRepo roleRepository;
    @Autowired
    private AdminService adminService;

    @PutMapping("/promote/{id}")
    public ResponseEntity<String> promoteToAdmin(@PathVariable Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        Role adminRole = roleRepository.findByRoleName(AppRole.ADMIN)
                .orElseThrow(() -> new ResourceNotFoundException("Admin role not found"));

        if (user.getRoles().contains(adminRole)) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(user.getUsername() + " is already an ADMIN");
        }

        user.getRoles().add(adminRole);

        userRepository.save(user);

        return ResponseEntity.ok(user.getUsername() + " Promoted successfully for an admin");
    }


    @GetMapping("/expenses")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ExpenseResponseDTO>> getAllExpenses() {

        return ResponseEntity.ok(adminService.getAllExpenses());
    }

    @GetMapping("/expenses/pending")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ExpenseResponseDTO>> getPendingExpenses() {

        return ResponseEntity.ok(adminService.getPendingExpenses());
    }

    @PutMapping("/expenses/{id}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> approveExpense(@PathVariable Long id) {

        adminService.approveExpense(id);
        return ResponseEntity.ok("Expense approved successfully");
    }

    @PutMapping("/expenses/{id}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> rejectExpense(@PathVariable Long id, @Valid @RequestBody RejectExpenseRequestDTO dto) {

        adminService.rejectExpense(id, dto);

        return ResponseEntity.ok("Expense rejected successfully");
    }
}
