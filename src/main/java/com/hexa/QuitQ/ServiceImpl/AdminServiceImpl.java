package com.hexa.QuitQ.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hexa.QuitQ.DTO.AdminDto;
import com.hexa.QuitQ.DTO.AdminUserDto;
import com.hexa.QuitQ.Service.AdminService;
import com.hexa.QuitQ.entities.Admin;
import com.hexa.QuitQ.entities.User;
import com.hexa.QuitQ.exception.DuplicateEmailException;
import com.hexa.QuitQ.exception.ResourceNotFoundException;
import com.hexa.QuitQ.mapper.AdminMapper;
import com.hexa.QuitQ.repository.AdminRepository;
import com.hexa.QuitQ.repository.UserRepository;

@Service
public class AdminServiceImpl implements AdminService{
	@Autowired
    private AdminRepository adminRepository;
	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public AdminServiceImpl(UserRepository userRepository,AdminRepository adminRepository,PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.adminRepository = adminRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
    public Admin addAdmin(AdminUserDto adminUserDto) {
        try {
        	 User user = AdminMapper.mapToUser(adminUserDto);
             

            if (userRepository.findByEmail(user.getEmail()) != null) {
                throw new DuplicateEmailException("A user with this email already exists: " + user.getEmail());
            }
            Admin admin = new Admin();
            admin.setAdminRole(adminUserDto.getAdminRole());
            admin.setUsers(user);
            admin.getUsers().setPassword(passwordEncoder.encode(adminUserDto.getPassword()));
            Admin savedAdmin = adminRepository.save(admin);
            return savedAdmin;
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Email already exists.");
        }
    }
    @Override
    public Admin getAdminById(Long id) throws ResourceNotFoundException {
        return adminRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Admin", "id", id));
    }
    
    @Override
    public Admin updateAdmin(String email, AdminDto adminDto) throws ResourceNotFoundException {
    	if (this.userRepository.findByEmail(email) == null) {
	        throw new ResourceNotFoundException("user","email",email);
	    }
    	
    	if (!email.equals(adminDto.getEmail())) {
			System.out.println(97);
		    if (userRepository.findByEmail(adminDto.getEmail()) != null) {
	            throw new DuplicateEmailException("A user with this email already exists: " + adminDto.getEmail());
	        }
	    }
		
        User user = AdminMapper.mapToUserFromAdminDto(adminDto);
        User enteredUser = this.userRepository.findByEmail(email);
        Admin admin = this.adminRepository.findByUsers(enteredUser);

        enteredUser.setUser_name(user.getUser_name());
        enteredUser.setAddress(user.getAddress());
        enteredUser.setPhone_number(user.getPhone_number());
        enteredUser.setEmail(user.getEmail());
        enteredUser.setUserRole(user.getUserRole());

        // Update the admin's details
        admin.setAdminRole(adminDto.getAdminRole());
        admin.setUsers(enteredUser);

        return this.adminRepository.save(admin);
    }
    
    @Override
    public Admin findAdminByEmail(String email) {
        return adminRepository.findAdminByEmail(email);
    }
    

}