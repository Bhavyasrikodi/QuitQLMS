package com.hexa.QuitQ.EntityController;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hexa.QuitQ.DTO.CustomerDto;
import com.hexa.QuitQ.DTO.CustomerUserDto;
import com.hexa.QuitQ.Service.CustomerService;
import com.hexa.QuitQ.entities.Customer;
import com.hexa.QuitQ.exception.DuplicateEmailException;
import com.hexa.QuitQ.exception.ResourceDeletionException;
import com.hexa.QuitQ.exception.ResourceNotFoundException;
import com.hexa.QuitQ.mapper.CustomerMapper;
import com.hexa.QuitQ.mapper.CustomerUserMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/quitq/customer")
@ResponseBody
@CrossOrigin(origins="http://localhost:3000")
public class CustomerController {
	
	
	private final CustomerService customerService;
	private final CustomerMapper customerMapper;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerController(CustomerService customerService, ModelMapper modelMapper, CustomerMapper customerMapper) {
        this.customerService = customerService;
        this.modelMapper = modelMapper;
        this.customerMapper = customerMapper;
    }
	
//  //http://localhost:8080/api/v1/quitq/customer/addcustomer
//    @PostMapping("/addcustomer")
//    public ResponseEntity<?> addCustomer(@Valid @RequestBody CustomerUserDto customerUserDto) {
//        try {
//        	 Customer createdCustomer = customerService.addCustomer(customerUserDto);
//             CustomerDto createdCustomerDto =this.customerMapper.mapToCustomerDto(createdCustomer);
//            return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomerDto);
//        } catch (DuplicateEmailException e) {
//            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("An unexpected error occurred.");
//        }
//    }
    
  //	http://localhost:8080/api/v1/quitq/customer/getcustomer/1
    @GetMapping("/getcustomer/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id) throws ResourceNotFoundException {
        try {
            Customer customer = customerService.getCustomerById(id);
            CustomerDto customerDto = this.customerMapper.mapToCustomerDto(customer);
            return ResponseEntity.ok(customerDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
    }

    //	http://localhost:8080/api/v1/quitq/customer/email?email=john.doe@example.com
    @GetMapping("/email")
    public ResponseEntity<?> getCustomerByEmail(@RequestParam String email) throws ResourceNotFoundException {
    	try {
        Customer customer = customerService.findCustomerByEmail(email);
		CustomerDto customerDto = this.customerMapper.mapToCustomerDto(customer);
		return ResponseEntity.ok(customerDto);
		}catch(ResourceNotFoundException e) {
			return ResponseEntity.ok(e.getMessage());
		}
    }

    // http://localhost:8080/api/v1/quitq/customer/updatecustomer
    @PutMapping("/updatecustomer")
    public ResponseEntity<?> updateCustomer(
            @RequestParam String email, 
            @Valid @RequestBody CustomerDto customerDto) {
        try {
            Customer updatedCustomer = customerService.updateCustomer(email, customerDto);
            CustomerUserDto updatedCustomerDto = CustomerUserMapper.mapToCustomerUserDto(updatedCustomer);
            return ResponseEntity.ok(updatedCustomerDto);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(e.getMessage());
        } catch (DuplicateEmailException e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
    }

    //	http://localhost:8080/api/v1/quitq/customer/deletecustomer/1
    @DeleteMapping("/deletecustomer/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) throws ResourceNotFoundException {
        try {
            boolean isRemoved = customerService.deleteCustomer(id);
            if (isRemoved) {
                return ResponseEntity.ok("Deleted customer with id = "+id+" successfully!!");
            } else {
                return ResponseEntity.status(HttpStatus.OK)
                        .body("Customer not found for ID: " + id);
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Customer not found for ID: " + id);
        } catch (ResourceDeletionException e) {
            return ResponseEntity.status(HttpStatus.OK).body(e.getMessage());
        }
    }}