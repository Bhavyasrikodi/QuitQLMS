package com.hexa.QuitQ.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.hexa.QuitQ.DTO.JWTAuthResponse;
import com.hexa.QuitQ.DTO.LoginDto;
import com.hexa.QuitQ.DTO.UserDto;
import com.hexa.QuitQ.Service.AuthService;
import com.hexa.QuitQ.entities.User;
import com.hexa.QuitQ.exception.ResourceNotFoundException;
import com.hexa.QuitQ.mapper.UserMapper;
import com.hexa.QuitQ.repository.UserRepository;
import com.hexa.QuitQ.security.JwtTokenProvider;

import jakarta.validation.Valid;

@Service
public class AuthServiceImpl implements AuthService{
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private  JwtTokenProvider jwtTokenProvider;
	
	 public JWTAuthResponse  login(@Valid @RequestBody LoginDto loginDto) throws ResourceNotFoundException {
		 if (!(userRepository.existsByEmail(loginDto.getEmail()))) {
		        throw  new ResourceNotFoundException("User", "email", loginDto.getEmail());
		    } 
		 else {
			 User user = userRepository.findByEmail(loginDto.getEmail());
			 Authentication authentication = authenticationManager.authenticate(
		    			new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
		    SecurityContextHolder.getContext().setAuthentication(authentication);
		    String token = jwtTokenProvider.generateToken(authentication);
	        UserDto userDto = UserMapper.mapToUserDto(user);
	        JWTAuthResponse response = new JWTAuthResponse(token, userDto.getEmail(), userDto.getUserRole(), userDto.getUser_name());
	        return response;
	    }
	 }
}
