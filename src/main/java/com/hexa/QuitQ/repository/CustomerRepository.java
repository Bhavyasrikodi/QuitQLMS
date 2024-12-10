package com.hexa.QuitQ.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hexa.QuitQ.entities.Customer;
import com.hexa.QuitQ.entities.User;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	@Query("SELECT c FROM Customer c JOIN c.users u WHERE u.email = :email")
	Customer findCustomerByEmail(@Param("email") String email);

	Customer findByUsers(User user);

}