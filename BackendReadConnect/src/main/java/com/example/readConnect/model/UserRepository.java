package com.example.readConnect.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query("SELECT u FROM User u WHERE u.email = ?1 OR u.username = ?1")
	User findByEmailOrUsername(String email, String username);
	
    //method to check if email exists
    boolean existsByEmail(String email);
    
    User findByUsername(String username); 

}


