package com.hexa.QuitQ.EntityController;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.hexa.QuitQ.DTO.AdminUserDto;
import com.hexa.QuitQ.DTO.CustomerUserDto;
import com.hexa.QuitQ.DTO.JWTAuthResponse;
import com.hexa.QuitQ.DTO.LMSUserRequestDto;
import com.hexa.QuitQ.DTO.LoginDto;
import com.hexa.QuitQ.DTO.SellerUserDto;
import com.hexa.QuitQ.DTO.UserDto;
import com.hexa.QuitQ.Service.AdminService;
import com.hexa.QuitQ.Service.AuthService;
import com.hexa.QuitQ.Service.CustomerService;
import com.hexa.QuitQ.Service.SellerService;
import com.hexa.QuitQ.entities.Admin;
import com.hexa.QuitQ.entities.Customer;
import com.hexa.QuitQ.entities.Seller;
import com.hexa.QuitQ.enums.UserRole;
import com.hexa.QuitQ.exception.DuplicateEmailException;
import com.hexa.QuitQ.exception.ResourceNotFoundException;
import com.hexa.QuitQ.mapper.AdminMapper;
import com.hexa.QuitQ.mapper.CustomerMapper;
import com.hexa.QuitQ.mapper.SellerMapper;
import com.hexa.QuitQ.security.JwtTokenProvider;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/quitq/auth")
@CrossOrigin(origins="*")
public class AuthController {
    
	private final CustomerService customerService;
    private final SellerService sellerService;
    private final AdminService adminService;
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    private final CustomerMapper customerMapper;
    private final SellerMapper sellerMapper;

    @Autowired
    private RestTemplate restTemplate;
    public AuthController(
        CustomerService customerService, 
        SellerService sellerService,
        AdminService adminService,
        JwtTokenProvider jwtTokenProvider,
        CustomerMapper customerMapper,
        SellerMapper sellerMapper,AuthService authService
        
    ) {
        this.customerService = customerService;
        this.sellerService = sellerService;
        this.adminService = adminService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.customerMapper = customerMapper;
        this.sellerMapper = sellerMapper;
        this.authService = authService;
    }

    //	http://localhost:8080/api/v1/quitq/auth/customer/register
    @PostMapping("customer/register")
    public ResponseEntity<String> register(@Valid @RequestBody CustomerUserDto customerUserDto) {
	        
    	try {
    		Customer createdCustomer = customerService.addCustomer(customerUserDto);
	        UserDto userDto = this.customerMapper.mapToUserDto(createdCustomer);
            LMSUserRequestDto lmsUserRequestDto = new LMSUserRequestDto();
            lmsUserRequestDto.setUserId(createdCustomer.getCustomer_id());
            lmsUserRequestDto.setPartnerId(createdCustomer.getUsers().getPartnerId());
            LMSUserRequestDto lmsResponse = restTemplate.postForObject("http://localhost:8080/api/v1/lms/users/createUser", lmsUserRequestDto, LMSUserRequestDto.class);
	        String token = jwtTokenProvider.generateToken(
	            new UsernamePasswordAuthenticationToken(createdCustomer.getUsers().getEmail(), createdCustomer.getUsers().getPassword())
	        );
	        //JWTAuthResponse response = new JWTAuthResponse(token, userDto);
	        if(token!=null) {
	        	return ResponseEntity.ok("Customer registered Successfully!!");
	    	}
	        else {
	        	return ResponseEntity.ok("Unsuccessful registration");
	        }
    	}catch (DuplicateEmailException e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
	    }
    }
    
    //	http://localhost:8080/api/v1/quitq/auth/seller/registerseller
    @PostMapping("/seller/registerseller")
    public ResponseEntity<String> registerSeller(@Valid @RequestBody SellerUserDto sellerUserDto) {
        try{
        	Seller createdSeller = sellerService.createSeller(sellerUserDto);
	        UserDto userDto = sellerMapper.mapToUserDto(createdSeller);
	        String token = jwtTokenProvider.generateToken(
	            new UsernamePasswordAuthenticationToken(createdSeller.getUsers().getEmail(), createdSeller.getUsers().getPassword())
	        );
	        if(token!=null) {
	        	return ResponseEntity.ok("Seller successfully registered");
	        }
	        else {
	        	return ResponseEntity.ok("Unsuccessful registration");
	        }
        }catch (DuplicateEmailException e) {
	       	return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
    }
    
    //	http://localhost:8080/api/v1/quitq/auth/admin/registeradmin
    @PostMapping("/admin/registeradmin")
    public ResponseEntity<String> registerAdmin(@Valid @RequestBody AdminUserDto adminUserDto) {
    	try{  
    		Admin createdAdmin = adminService.addAdmin(adminUserDto);
	        UserDto userDto = AdminMapper.mapToUserDto(createdAdmin);
	        String token = jwtTokenProvider.generateToken(
	            new UsernamePasswordAuthenticationToken(createdAdmin.getUsers().getEmail(), createdAdmin.getUsers().getPassword())
	        );
	        if(token!=null) {
	        	return ResponseEntity.ok("Admin registered successfully");
	        }
	        else {
	        	return ResponseEntity.ok("Unsuccessful registration");
	        }
	    }catch (DuplicateEmailException e) {
	       	return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
	    }
    }

    //	http://localhost:8080/api/v1/quitq/auth/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto loginDto) throws ResourceNotFoundException {
        try {
            // Call the authService to perform the login and get the JWT token and role information
            JWTAuthResponse jwtAuthResponse = authService.login(loginDto);
            
            // Set the ID based on the user role
            if (jwtAuthResponse.getUserRole().equals(UserRole.Customer)) {
                Customer customer = customerService.findCustomerByEmail(jwtAuthResponse.getEmail());
                jwtAuthResponse.setId(customer.getCustomer_id());
            } else if (jwtAuthResponse.getUserRole().equals(UserRole.Seller)) {
                Seller seller = sellerService.findSellerByEmail(jwtAuthResponse.getEmail());
                jwtAuthResponse.setId(seller.getSellers_id());
            } else if (jwtAuthResponse.getUserRole().equals(UserRole.Admin)) {
                Admin admin = adminService.findAdminByEmail(jwtAuthResponse.getEmail());
                jwtAuthResponse.setId(admin.getAdmin_id());
            }

            // Return the JWTAuthResponse with token and ID
            return ResponseEntity.ok(jwtAuthResponse);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during login.");
        }
    }


}
