package com.hexa.QuitQ.mapper;

import org.springframework.stereotype.Component;

import com.hexa.QuitQ.DTO.UserDto;
import com.hexa.QuitQ.entities.User;
@Component
public class UserMapper {


    // Map User entity to UserDto
    public static UserDto mapToUserDto(User entity) {
        UserDto dto = new UserDto();
        dto.setUser_name(entity.getUser_name());
        dto.setAddress(entity.getAddress());
        dto.setPhone_number(entity.getPhone_number());
        dto.setEmail(entity.getEmail());
        dto.setUserRole(entity.getUserRole());

        return dto;
    }

    // Map UserDto to User entity
    public static User mapToUser(UserDto dto) {
        User entity = new User();
        entity.setUser_name(dto.getUser_name());
        entity.setAddress(dto.getAddress());
        entity.setPhone_number(dto.getPhone_number());
        entity.setEmail(dto.getEmail());
        entity.setUserRole(dto.getUserRole());
      
        return entity;
    }

    
}