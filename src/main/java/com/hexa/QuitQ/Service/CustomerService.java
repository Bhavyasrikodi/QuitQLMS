package com.hexa.QuitQ.Service;

import java.util.List;

import com.hexa.QuitQ.DTO.CustomerDto;
import com.hexa.QuitQ.DTO.CustomerUserDto;
import com.hexa.QuitQ.entities.Customer;
import com.hexa.QuitQ.entities.User;
import com.hexa.QuitQ.exception.ResourceNotFoundException;

public interface CustomerService {
	Customer addCustomer(CustomerUserDto customerUserDto);

	Customer getCustomerById(Long id) throws ResourceNotFoundException;

	Customer findCustomerByEmail(String email) throws ResourceNotFoundException;

	long getCustomerCount();

	List<Customer> getAllCustomers();

	Customer updateCustomer(String email, CustomerDto customerDto) throws ResourceNotFoundException;

	boolean deleteCustomer(Long id) throws ResourceNotFoundException;
	public User findUserByEmail(String email);
}