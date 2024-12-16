package com.hexa.QuitQ.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.hexa.QuitQ.enums.UserRole;
import com.hexa.QuitQ.security.JwtAuthenticationEntryPoint;
import com.hexa.QuitQ.security.JwtAuthenticationFilter;
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private JwtAuthenticationEntryPoint authenticationEntryPoint;
	private JwtAuthenticationFilter jwtAuthenticationFilter;
	public SecurityConfig(
							JwtAuthenticationEntryPoint authenticationEntryPoint,
							JwtAuthenticationFilter jwtAuthenticationFilter) {
		this.authenticationEntryPoint = authenticationEntryPoint;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception
	{
		return configuration.getAuthenticationManager();
	}
	
	@Bean
	public static PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
	    // Disable CSRF protection explicitly (for stateless applications, typically used with JWT)
	    httpSecurity.cors().and().csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests((authorize) -> 
	            authorize
	                .requestMatchers("/api/v1/quitq/auth/**", "/api/v1/quitq/customer/**").permitAll()
	                //.requestMatchers("/api/v1/quitq/customer/**").hasRole(UserRole.Customer.name())
	                .requestMatchers("/api/v1/quitq/admin/**").hasRole(UserRole.Admin.name())
	                .requestMatchers("/api/v1/quitq/seller/**").hasRole(UserRole.Seller.name())
	                .requestMatchers("/api/v1/products/getallproducts").permitAll()
	                .requestMatchers("/api/v1/products/getproductbybrand").permitAll()
	                .requestMatchers("/api/v1/products/getproductbycategory").permitAll()
	                .requestMatchers("/api/v1/products/getsetofproductsbybrands").permitAll()
	                .requestMatchers("/api/v1/products/productswithpriceRange").permitAll()
	                .requestMatchers("/api/v1/products/getproductbyid/**").permitAll()
	                .requestMatchers("/api/v1/products/search").permitAll()
	                .requestMatchers("/api/v1/products/seller/**").hasRole(UserRole.Seller.name())
	                .requestMatchers("/api/v1/quitq/cart/**").hasRole(UserRole.Customer.name())
	                .requestMatchers("/api/quitq/payments/get/**").hasAnyRole(UserRole.Customer.name(),UserRole.Seller.name())
	                .requestMatchers("/api/quitq/payments/for/**").hasRole(UserRole.Customer.name())
	                .requestMatchers("/api/v1/quitq/orders/customer/**").hasRole(UserRole.Customer.name())
	                .requestMatchers("/api/v1/quitq/orders/status/**").hasAnyRole(UserRole.Customer.name(),UserRole.Seller.name())
	                .requestMatchers("/api/v1/quitq/orderitem/**").hasAnyRole(UserRole.Customer.name(),UserRole.Seller.name())
	                .anyRequest().authenticated()
	        )
	        .exceptionHandling(exception -> 
	            exception.authenticationEntryPoint(authenticationEntryPoint)
	        )
	        .sessionManagement(session -> 
	            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        );
	    
	    // Add JWT authentication filter
	    httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

	    return httpSecurity.build();
	}
	
	@Bean
	public CorsFilter corsFilter() {
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    CorsConfiguration config = new CorsConfiguration();
	    config.setAllowCredentials(true); // Allow credentials like Authorization headers
	    config.addAllowedOrigin("http://localhost:3001"); // Allow your frontend
	    config.addAllowedHeader("*"); // Allow all headers
	    config.addAllowedMethod("*"); // Allow all HTTP methods (GET, POST, PUT, DELETE, etc.)
	    source.registerCorsConfiguration("/**", config); // Apply this configuration to all endpoints
	    return new CorsFilter(source);
	}


}