package com.hexa.QuitQ.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexa.QuitQ.entities.Admin;
import com.hexa.QuitQ.exception.ResourceNotFoundException;

@SpringBootTest
public class AdminTest {

    @Autowired
    private AdminRepository adminRepository;

    // 1. Test for fetching admin by ID
    @Test
    public void testGetAdminById() throws ResourceNotFoundException {
        Long adminId = 1L;
        Optional<Admin> admin = adminRepository.findById(adminId);
        assertTrue(admin.isPresent()); 
        assertNotNull(admin.get());    
        System.out.println("Admin found: " + admin.get().getUsers().getUser_name());
    }

    // 2. Test for fetching admin by email
    @Test
    public void testFindAdminByEmail() throws ResourceNotFoundException {
        String email = "sholea@gmail.com";
        Admin admin = adminRepository.findAdminByEmail(email);
        assertNotNull(admin); 
        assertEquals(email, admin.getUsers().getEmail()); 
        System.out.println("Admin found with email: " + admin.getUsers().getEmail());
    }
}