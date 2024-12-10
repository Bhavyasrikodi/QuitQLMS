package com.hexa.QuitQ.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hexa.QuitQ.DTO.CustomerAdminDto;
import com.hexa.QuitQ.DTO.CustomerDto;
import com.hexa.QuitQ.DTO.UserDto;
import com.hexa.QuitQ.entities.Customer;
import com.hexa.QuitQ.entities.User;
@Component
public class CustomerMapper {

	@Autowired
	private ModelMapper modelMapper;

    public CustomerDto mapToCustomerDto(Customer entity) {
        if (entity.getUsers() != null) {
            CustomerDto dto = this.modelMapper.map(entity.getUsers(), CustomerDto.class);
            dto.setPaymentMode(entity.getPaymentMode());
            dto.setCustomer_id(entity.getCustomer_id());
            return dto;
        }
        
        return null;
    }

    // Map CustomerDto to Customer entity
    public Customer mapToCustomer(CustomerDto dto) {
        Customer entity = new Customer();
        entity.setPaymentMode(dto.getPaymentMode());
        if (dto.getUser_name() != null) {
            User user = this.modelMapper.map(dto, User.class);
            entity.setUsers(user);
        }
        return entity;
    }

    // Map list of Customer entities to list of CustomerDto
    public List<CustomerDto> mapToCustomerDto(List<Customer> entities) {
        List<CustomerDto> dtoList = new ArrayList<>();
        for (Customer entity : entities) {
            dtoList.add(mapToCustomerDto(entity));
        }
        return dtoList;
    }

    // Map iterable of CustomerDto to list of Customer entities
    public List<Customer> mapToCustomer(Iterable<CustomerDto> dtoIterable) {
        List<Customer> entities = new ArrayList<>();
        for (CustomerDto dto : dtoIterable) {
            entities.add(mapToCustomer(dto));
        }
        return entities;
    }

    // Map User entity to UserDto
    private UserDto mapToUserDto(User entity) {
        UserDto dto = new UserDto();
        dto.setUser_name(entity.getUser_name());
        dto.setAddress(entity.getAddress());
        dto.setPhone_number(entity.getPhone_number());
        dto.setEmail(entity.getEmail());
        dto.setUserRole(entity.getUserRole());
        return dto;
    }

    // Map UserDto to User entity
    private User mapToUser(UserDto dto) {
        User entity = new User();
        entity.setUser_name(dto.getUser_name());
        entity.setAddress(dto.getAddress());
        entity.setPhone_number(dto.getPhone_number());
        entity.setEmail(dto.getEmail());
        entity.setUserRole(dto.getUserRole());
        return entity;
    }
    
    public UserDto mapToUserDto(Customer customer) {
        User user = customer.getUsers(); 
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setUser_name(user.getUser_name());
        userDto.setAddress(user.getAddress());
        userDto.setPhone_number(user.getPhone_number());
        userDto.setEmail(user.getEmail());
        userDto.setUserRole(user.getUserRole());
        return userDto;
    }
    // Map Customer to CustomerAdminDto
    public CustomerAdminDto mapToCustomerAdminDto(Customer entity) {
        CustomerAdminDto dto = new CustomerAdminDto();
        dto.setCustomer_id(entity.getCustomer_id()); 
        dto.setUser_name(entity.getUsers().getUser_name()); 
        dto.setAddress(entity.getUsers().getAddress());
        dto.setPhone_number(entity.getUsers().getPhone_number());
        dto.setEmail(entity.getUsers().getEmail());
        dto.setPaymentMode(entity.getPaymentMode());
        return dto;
    }
    // Map list of Customer entities to list of CustomerAdminDto
    public List<CustomerAdminDto> mapToCustomerAdminDto(List<Customer> entities) {
        List<CustomerAdminDto> dtoList = new ArrayList<>();
        for (Customer entity : entities) {
            dtoList.add(mapToCustomerAdminDto(entity));
        }
        return dtoList;
    }

}