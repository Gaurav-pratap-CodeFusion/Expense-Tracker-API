package com.gpcf.expense_tracker_api.Admin;

import com.gpcf.expense_tracker_api.entity.Role;
import com.gpcf.expense_tracker_api.entity.User;
import com.gpcf.expense_tracker_api.enums.AppRole;
import com.gpcf.expense_tracker_api.repository.RoleRepo;
import com.gpcf.expense_tracker_api.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {

    @Autowired
    private UserRepo userRepository;
    @Autowired
    private RoleRepo roleRepository;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public void run(String... args) {

        if (roleRepository.findByRoleName(AppRole.ADMIN).isEmpty()) {
            roleRepository.save(new Role(AppRole.ADMIN));
            roleRepository.save(new Role(AppRole.USER));
        }

        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(encoder.encode("admin123"));
            admin.setEmail("admin123@gmail.com");
            admin.setName("Admin");

            Role adminRole = roleRepository.findByRoleName(AppRole.ADMIN).get();
            admin.getRoles().add(adminRole);

            userRepository.save(admin);
        }
    }
}

