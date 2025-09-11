package com.academiq.config;

import com.academiq.model.AppUser;
import com.academiq.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Create admin user if not exists
        if (userRepository.findByUsername("admin").isEmpty()) {
            AppUser admin = new AppUser();
            admin.setUsername("admin");
            admin.setEmail("admin@academiq.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setFirstName("Admin");
            admin.setLastName("User");
            admin.setActive(true);
            admin.setRoles(new HashSet<>(Collections.singletonList("ADMIN")));
            userRepository.save(admin);
            System.out.println("Admin user created");
        }

        // Create test student user if not exists
        if (userRepository.findByUsername("student").isEmpty()) {
            AppUser student = new AppUser();
            student.setUsername("student");
            student.setEmail("student@academiq.com");
            student.setPassword(passwordEncoder.encode("student123"));
            student.setFirstName("Test");
            student.setLastName("Student");
            student.setActive(true);
            student.setRoles(new HashSet<>(Collections.singletonList("STUDENT")));
            userRepository.save(student);
            System.out.println("Student user created");
        }

        // Create test staff user if not exists
        if (userRepository.findByUsername("staff").isEmpty()) {
            AppUser staff = new AppUser();
            staff.setUsername("staff");
            staff.setEmail("staff@academiq.com");
            staff.setPassword(passwordEncoder.encode("staff123"));
            staff.setFirstName("Test");
            staff.setLastName("Staff");
            staff.setActive(true);
            staff.setRoles(new HashSet<>(Collections.singletonList("STAFF")));
            userRepository.save(staff);
            System.out.println("Staff user created");
        }
    }
}