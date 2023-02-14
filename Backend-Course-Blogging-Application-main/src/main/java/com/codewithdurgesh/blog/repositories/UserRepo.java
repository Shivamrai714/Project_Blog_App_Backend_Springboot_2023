package com.codewithdurgesh.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithdurgesh.blog.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>{
		
	//Just extending the Jpa repository , it will internally have all coding for update,create,delete,get, we just need to call the methods from the servicesImpl class.	
	
	
	// This is CustomFinderMethod , by this syntax "findBy(FieldName)" , springboot  will itself understand it and implement it.
	
	Optional<User> findByEmail(String email);   
	
}
