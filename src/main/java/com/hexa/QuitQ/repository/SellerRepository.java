package com.hexa.QuitQ.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hexa.QuitQ.entities.Seller;
import com.hexa.QuitQ.entities.User;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {


	Optional<Seller> findById(Long id);
	Seller findByUsers(User user);
	
	 @Query("SELECT s FROM Seller s JOIN s.users u WHERE u.email = :email")
	    Seller findSellerByEmail(@Param("email") String email);
}
