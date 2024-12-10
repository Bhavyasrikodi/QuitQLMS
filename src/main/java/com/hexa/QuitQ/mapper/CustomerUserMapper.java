package com.hexa.QuitQ.mapper;

import com.hexa.QuitQ.DTO.CustomerDto;
import com.hexa.QuitQ.DTO.CustomerUserDto;
import com.hexa.QuitQ.DTO.UserDto;
import com.hexa.QuitQ.entities.Customer;
import com.hexa.QuitQ.entities.User;

public class CustomerUserMapper {
	public static CustomerUserDto mapToCustomerUserDto(Customer customer) {
        CustomerUserDto dto = new CustomerUserDto();
        dto.setPaymentMode(customer.getPaymentMode());
        // Assuming you need to also set User properties if available
        User user = customer.getUsers();
        if (user != null) {
            dto.setUser_name(user.getUser_name());
            dto.setAddress(user.getAddress());
            dto.setPhone_number(user.getPhone_number());
            dto.setEmail(user.getEmail());
            dto.setPassword(user.getPassword());
            dto.setUserRole(user.getUserRole());
            dto.setPartnerId(user.getPartnerId());
        }
        return dto;
    }
	
	public static User mapToUser(CustomerUserDto dto) {
        User entity = new User();
        entity.setUser_name(dto.getUser_name());
        entity.setAddress(dto.getAddress());
        entity.setPhone_number(dto.getPhone_number());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setUserRole(dto.getUserRole());
        entity.setPartnerId(dto.getPartnerId());
        return entity;
    }

}