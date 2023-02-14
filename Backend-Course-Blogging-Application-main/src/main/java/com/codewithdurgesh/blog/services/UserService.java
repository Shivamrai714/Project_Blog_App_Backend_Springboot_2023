package com.codewithdurgesh.blog.services;

import java.util.List;

import com.codewithdurgesh.blog.payloads.UserDto;

// CONTROLLER (handler-method) -->  USER_SERVICE(declare-M) --> USER_SERVICE_IMPL(call-M)   --> USER_REPO(impl_M-springboot) 


public interface UserService {

	// Here we will declare all the methods to be used by the User-Controller 
	// UserDto is just created to avoid internal looping in fetching data , and also secure the main Entity.
	
	UserDto registerNewUser(UserDto user);
	
	
	
	
	UserDto createUser(UserDto user);

	UserDto updateUser(UserDto user, Integer userId);

	UserDto getUserById(Integer userId);

	List<UserDto> getAllUsers();

	void deleteUser(Integer userId);
	
	
	

}
