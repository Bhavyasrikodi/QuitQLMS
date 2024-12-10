package com.hexa.QuitQ.Service;
 
import org.springframework.web.bind.annotation.RequestBody;

import com.hexa.QuitQ.DTO.JWTAuthResponse;
import com.hexa.QuitQ.DTO.LoginDto;
import com.hexa.QuitQ.exception.ResourceNotFoundException;

import jakarta.validation.Valid;
 
public interface AuthService {
	JWTAuthResponse  login(@Valid @RequestBody LoginDto loginDto) throws ResourceNotFoundException;
	//String register(RegisterDto dto);
}