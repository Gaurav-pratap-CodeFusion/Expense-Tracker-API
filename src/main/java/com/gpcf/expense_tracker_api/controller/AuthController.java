package com.gpcf.expense_tracker_api.controller;

import com.gpcf.expense_tracker_api.dto.AuthResponseDTO;
import com.gpcf.expense_tracker_api.dto.LoginRequestDTO;
import com.gpcf.expense_tracker_api.dto.RegisterRequestDTO;
import com.gpcf.expense_tracker_api.service.imp.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequestDTO registerRequest) {
        String response = authService.register(registerRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> authenticateUser(@Valid @RequestBody LoginRequestDTO loginRequest) {
        AuthResponseDTO response = authService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

