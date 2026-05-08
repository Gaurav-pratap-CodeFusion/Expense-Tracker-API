package com.gpcf.expense_tracker_api.security.service;

import com.gpcf.expense_tracker_api.entity.User;
import com.gpcf.expense_tracker_api.exception.ResourceNotFoundException;
import com.gpcf.expense_tracker_api.repository.UserRepo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    public CustomUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username).orElseThrow(() ->
                new ResourceNotFoundException("User Not Found"));

        List<SimpleGrantedAuthority> roleList = user.getRoles().stream().map((role) ->
                new SimpleGrantedAuthority("ROLE_" + role.getRoleName())).toList();

        UserDetails build = org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(roleList)
                .build();

        return build;
    }
}
