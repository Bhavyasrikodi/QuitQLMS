package com.hexa.QuitQ.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hexa.QuitQ.entities.Admin;
import com.hexa.QuitQ.entities.User;

@Repository
public interface AdminRepository  extends JpaRepository<Admin, Long>{
	Admin findByUsers(User user);
	
	@Query("SELECT a FROM Admin a JOIN a.users u WHERE u.email = :email")
	Admin findAdminByEmail(@Param("email") String email);

}
