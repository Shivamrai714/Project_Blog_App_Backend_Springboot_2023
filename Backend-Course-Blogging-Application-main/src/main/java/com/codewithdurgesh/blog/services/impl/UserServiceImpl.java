package com.codewithdurgesh.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codewithdurgesh.blog.exceptions.*;
import com.codewithdurgesh.blog.payloads.UserDto;
import com.codewithdurgesh.blog.services.UserService;
import com.codewithdurgesh.blog.repositories.*;
import com.codewithdurgesh.blog.config.AppConstants;
import com.codewithdurgesh.blog.entities.*;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;    //Its just used to convert one class data to other User To UserDto & vice-versa

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepo roleRepo;
	
//---------------------------------//---------------------------------//---------------------------------	
		
	// 1. CREATE-USER HANDLER METHOD

	@Override
	public UserDto createUser(UserDto userDto)  // The data will be available in @RequestBody in Controller ie (in JSON format { . . }  if using POSTMAN , otherwise formsubmission() data if using frontend) 
	{
		User user = this.dtoToUser(userDto);       //we can directy use modelmapper.map() here  to convert data from "UserDto" to "User" to save it in database.
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		
		//done this later - to check after introducing roles,  direct user crated will be ADMIN 501		
		//Role role = new Role();role.setId(501); role.setName("ROLE_ADMIN");
		//user.getRoles().add(role);
		
		User savedUser = this.userRepo.save(user);
		return this.userToDto(savedUser);
	}

//---------------------------------//---------------------------------//---------------------------------	
	
	// 2. UPDATE - USER 
	
	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {

		//First get the old user with userId given  ,  handled Global Exception
		
		User user = this.userRepo.findById(userId).orElseThrow(   () -> new ResourceNotFoundException("User", " Id ", userId)   );

		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(this.passwordEncoder.encode(userDto.getPassword()) );
		user.setAbout(userDto.getAbout());
		                                                     //ROLE : we are not allowing to change.

		//save in database
		User updatedUser = this.userRepo.save(user);
		
		//return back object of "UserDto"
		UserDto userDto1 = this.userToDto(updatedUser);
		return userDto1;
	
	}

//---------------------------------//---------------------------------//---------------------------------
	
	// 3 . GET-SINGLE-USER 
	
	@Override
	public UserDto getUserById(Integer userId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));

		return this.userToDto(user);
	}
	
//---------------------------------//---------------------------------//---------------------------------
	
	// 4. GET ALL USERS
	@Override
	public List<UserDto> getAllUsers() {

		List<User> users = this.userRepo.findAll();
		List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());

		return userDtos;
	}

//---------------------------------//---------------------------------//---------------------------------	
	
	//5 . DELETE USER
	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		this.userRepo.delete(user);

	}
	
//---------------------------------//---------------------------------//---------------------------------	
	//6. Explicit method to convert the object.
	
	public User dtoToUser(UserDto userDto) {
		
		//model mapper can direclty do the below manual conversion.
		User user = this.modelMapper.map(userDto, User.class);

		// user.setId(userDto.getId());
		// user.setName(userDto.getName());
		// user.setEmail(userDto.getEmail());
		// user.setAbout(userDto.getAbout());
		// user.setPassword(userDto.getPassword());
		return user;
	}

	public UserDto userToDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		return userDto;
	}
	
//---------------------------------//---------------------------------//---------------------------------	
	
	
	// 7. REGISTER THE USER /NORMAL USER  :  For frontend application of React to register user as Normal Uses

	@Override
	public UserDto registerNewUser(UserDto userDto) {

		User user = this.modelMapper.map(userDto, User.class);

		// encoded the password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));

		// roles - find the normal role object and add it to the user hashSet using add().
		Role role = this.roleRepo.findById(AppConstants.NORMAL_USER).get();
		user.getRoles().add(role);

		//save in DB
		User newUser = this.userRepo.save(user);

		return this.modelMapper.map(newUser, UserDto.class);
		
	}
	
//---------------------------------//---------------------------------//---------------------------------
	

}
