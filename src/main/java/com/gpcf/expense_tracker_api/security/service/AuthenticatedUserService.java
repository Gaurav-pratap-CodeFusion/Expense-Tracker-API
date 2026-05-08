package com.gpcf.expense_tracker_api.security.service;

import com.gpcf.expense_tracker_api.entity.User;
import com.gpcf.expense_tracker_api.exception.ResourceNotFoundException;
import com.gpcf.expense_tracker_api.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticatedUserService {

    private final UserRepo userRepository;

    public User getLoggedInUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
