package com.gpcf.expense_tracker_api.service.imp;

import com.gpcf.expense_tracker_api.dto.AuthResponseDTO;
import com.gpcf.expense_tracker_api.dto.LoginRequestDTO;
import com.gpcf.expense_tracker_api.dto.RegisterRequestDTO;
import com.gpcf.expense_tracker_api.entity.Role;
import com.gpcf.expense_tracker_api.entity.User;
import com.gpcf.expense_tracker_api.enums.AppRole;
import com.gpcf.expense_tracker_api.exception.ResourceNotFoundException;
import com.gpcf.expense_tracker_api.exception.UserAlreadyExistsException;
import com.gpcf.expense_tracker_api.repository.RoleRepo;
import com.gpcf.expense_tracker_api.repository.UserRepo;
import com.gpcf.expense_tracker_api.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepo userRepository;
    private final RoleRepo roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtUtils;

    public String register(RegisterRequestDTO dto) {

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new UserAlreadyExistsException("Username already taken");
        }

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new UserAlreadyExistsException("Email already registered");
        }

        User user = new User();
        user.setName(dto.getName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        Role userRole = roleRepository.findByRoleName(AppRole.USER)
                .orElseThrow(() -> new ResourceNotFoundException("Error: Role not found."));

        user.getRoles().add(userRole);

        userRepository.save(user);
        return "User registered successfully!";
    }

    public AuthResponseDTO login(LoginRequestDTO dto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwt(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority()).toList();

        return new AuthResponseDTO(jwt, "Bearer", userDetails.getUsername(), roles);
    }
}

