package com.codewithdurgesh.blog.controllers;

//   	@RequestBody is used to collect the JSON data , sent from POSTMAN { "username": "" , ...}
//      @PathVariable is used to fetch the value from the url eg { userid=5 from :   http://localhost:9292/api/v1/user/5   }
//      @RequestParam  to fetch the form data etc .

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codewithdurgesh.blog.payloads.ApiResponse;
import com.codewithdurgesh.blog.payloads.UserDto;
import com.codewithdurgesh.blog.services.UserService;

@CrossOrigin("*")                 //This allow application to access for other port of react.Application
@RestController
@RequestMapping("/api/v1/users")
public class UserController 
{

	
	@Autowired
	private UserService userService;

	
//----------------------------------------------------------------------------------------------	
	//    POST-create user
	
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)  // @Valid is used to apply validation set on UserDto class
	{
		UserDto createUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
	}

//----------------------------------------------------------------------------------------------	
	// PUT- update user

	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer uid) {
		UserDto updatedUser = this.userService.updateUser(userDto, uid);
		return ResponseEntity.ok(updatedUser);
	}

	
//----------------------------------------------------------------------------------------------	
	
		// GET ALL - user get
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers() 
	{
			return ResponseEntity.ok(this.userService.getAllUsers());
	}

//----------------------------------------------------------------------------------------------	
		
	// GET SINGLE - user get
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable Integer userId) 
	{
			return ResponseEntity.ok(this.userService.getUserById(userId));
	}



//----------------------------------------------------------------------------------------------		

	// DELETE -delete user   Only admin can delete
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid) {
		this.userService.deleteUser(uid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfully", true), HttpStatus.OK);
	}

//----------------------------------------------------------------------------------------------		
	
}
