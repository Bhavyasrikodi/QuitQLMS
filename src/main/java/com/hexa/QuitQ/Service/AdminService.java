package com.hexa.QuitQ.Service;

import com.hexa.QuitQ.DTO.AdminDto;
import com.hexa.QuitQ.DTO.AdminUserDto;
import com.hexa.QuitQ.entities.Admin;
import com.hexa.QuitQ.exception.ResourceNotFoundException;

public interface AdminService {
	Admin addAdmin(AdminUserDto adminUserDto);
	Admin getAdminById(Long id) throws ResourceNotFoundException;
	Admin updateAdmin(String email, AdminDto adminDto) throws ResourceNotFoundException;
	Admin findAdminByEmail(String email);
	
}