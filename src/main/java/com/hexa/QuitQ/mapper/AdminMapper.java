package com.hexa.QuitQ.mapper;

import java.util.ArrayList;
import java.util.List;

import com.hexa.QuitQ.DTO.AdminDto;
import com.hexa.QuitQ.DTO.AdminUserDto;
import com.hexa.QuitQ.DTO.UserDto;
import com.hexa.QuitQ.entities.Admin;
import com.hexa.QuitQ.entities.User;

public class AdminMapper {

    // Map Admin entity to AdminDto
    public static AdminDto mapToAdminDto(Admin entity) {
        if (entity == null) {
            return null;
        }
        AdminDto dto = new AdminDto();
        dto.setAdminRole(entity.getAdminRole());
        if (entity.getUsers() != null) {
            dto.setUser_name(entity.getUsers().getUser_name());
            dto.setAddress(entity.getUsers().getAddress());
            dto.setEmail(entity.getUsers().getEmail());
            dto.setPhone_number(entity.getUsers().getPhone_number());
            dto.setUserRole(entity.getUsers().getUserRole());
        }
        return dto;
    }

    // Map list of Admin entities to list of AdminDto
    public static List<AdminDto> mapToAdminDto(List<Admin> entities) {
        List<AdminDto> dtoList = new ArrayList<>();
        for (Admin entity : entities) {
            dtoList.add(mapToAdminDto(entity));
        }
        return dtoList;
    }

    // Map User entity to UserDto
    private static UserDto mapToUserDto(User entity) {
        if (entity == null) {
            return null;
        }
        UserDto dto = new UserDto();
        dto.setUser_name(entity.getUser_name());
        dto.setAddress(entity.getAddress());
        dto.setPhone_number(entity.getPhone_number());
        dto.setEmail(entity.getEmail());
        dto.setUserRole(entity.getUserRole());
        return dto;
    }

    // Map UserDto to User entity
    private static User mapToUser(UserDto dto) {
        if (dto == null) {
            return null;
        }
        User entity = new User();
        entity.setUser_name(dto.getUser_name());
        entity.setAddress(dto.getAddress());
        entity.setPhone_number(dto.getPhone_number());
        entity.setEmail(dto.getEmail());
        entity.setUserRole(dto.getUserRole());
        return entity;
    }
    
    
    public static AdminUserDto mapToAdminUserDto(Admin admin) {
        AdminUserDto dto = new AdminUserDto();
        dto.setAdminRole(admin.getAdminRole());
        if (admin.getUsers() != null) {
            User user = admin.getUsers();
            dto.setUser_name(user.getUser_name());
            dto.setAddress(user.getAddress());
            dto.setPhone_number(user.getPhone_number());
            dto.setEmail(user.getEmail());
            dto.setPassword(user.getPassword());
            dto.setUserRole(user.getUserRole());
        }
        return dto;
    }

    public static Admin mapToAdmin(AdminUserDto dto) {
        Admin entity = new Admin();
        entity.setAdminRole(dto.getAdminRole());
        User user = new User();
        user.setUser_name(dto.getUser_name());
        user.setAddress(dto.getAddress());
        user.setPhone_number(dto.getPhone_number());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setUserRole(dto.getUserRole());
        entity.setUsers(user);
        return entity;
    }
    public static User mapToUser(AdminUserDto dto) {
        User user = new User();
        user.setUser_name(dto.getUser_name());
        user.setAddress(dto.getAddress());
        user.setPhone_number(dto.getPhone_number());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setUserRole(dto.getUserRole());
        return user;
    }
    public static UserDto mapToUserDto(Admin admin) {
        if (admin == null || admin.getUsers() == null) {
            return null;
        }
        return mapToUserDto(admin.getUsers());
    }
    public static User mapToUserFromAdminDto(AdminDto dto) {
        if (dto == null) {
            return null;
        }
        User user = new User();
        user.setUser_name(dto.getUser_name());
        user.setAddress(dto.getAddress());
        user.setPhone_number(dto.getPhone_number());
        user.setEmail(dto.getEmail());
        user.setUserRole(dto.getUserRole());
        return user;
    }
}
