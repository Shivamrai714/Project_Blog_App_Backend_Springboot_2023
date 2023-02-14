package com.codewithdurgesh.blog;

import java.util.HashSet;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.codewithdurgesh.blog.config.AppConstants;
import com.codewithdurgesh.blog.entities.Category;
import com.codewithdurgesh.blog.entities.Role;
import com.codewithdurgesh.blog.entities.User;
import com.codewithdurgesh.blog.repositories.CategoryRepo;
import com.codewithdurgesh.blog.repositories.RoleRepo;
import com.codewithdurgesh.blog.repositories.UserRepo;
import com.codewithdurgesh.blog.services.CategoryService;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {


	public static void main(String[] args) 
	{
		SpringApplication.run(BlogAppApisApplication.class, args);
	}

	
	// DECLARE BEAN of Model Mapper  : to convert one class object to other class.
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	
	// Autowiring variable to set the Intital Users
	
	@Autowired
	private RoleRepo roleRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private CategoryRepo categoryRepo;
	
	
// Its is the method to run the project at intial setup // CommandLineRunner method.
	
	@Override
	public void run(String... args) throws Exception {

		System.out.println("\nStarting the project .. Edited By Shivam\n");
		//System.out.println(this.passwordEncoder.encode("xyz"));
		//System.out.println(this.bCryptPasswordEncoder.encode("123"));
		

		try {

			//setting the data in role table
			
			Role role = new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ROLE_ADMIN");

			Role role1 = new Role();
			role1.setId(AppConstants.NORMAL_USER);
			role1.setName("ROLE_NORMAL");

		
			
			
	                                                     //List<Role> roles = List.of(role1);

			
//			//Manually i have  set the ADMIN USER and the categories    : I HAVE CREATED THE CATEGORIES AND THE ADMIN USER EXTENALLY HERE , 
			//Run it one time after creating the database. Otherwise the duplicate  key problem
//	
			
			//creating the ADMIN USER  :  create the HashSet , create the Role object , add role obj in Set and using setter of Hashset , save it to user , and finally save user to database using Repo.: 
		
		/*	
			User user= new User();
			user.setName("SHIVAM RAI");
			user.setEmail("shivamrai714@gmail.com");
			user.setAbout("Main ADMIN and programmer of Application");
			user.setPassword(this.passwordEncoder.encode("123"));
			
			HashSet<Role> rr= new HashSet<>();
			rr.add(role);                     //adding the admin Role obj to user
			user.setRoles(rr);
			
	        this.userRepo.save(user);
	        
	        Category category1= new Category();   category1.setCategoryId(205); category1.setCategoryTitle("Entertainment"); category1.setCategoryDescription("Collection of music , debates , tv etc ");
	        Category category2= new Category();  category2.setCategoryId(202); category2.setCategoryTitle("Education"); category2.setCategoryDescription("A platform for Education and knowledge");
	        Category category3= new Category(); category3.setCategoryId(203); category3.setCategoryTitle("Programming"); category3.setCategoryDescription("Coding and implementaion");
	        Category category5= new Category(); category5.setCategoryId(205); category5.setCategoryTitle("Devotional"); category5.setCategoryDescription("Includes the collection of devotional songs , lectures ");
			Category category6= new Category(); category6.setCategoryId(206); category6.setCategoryTitle("Current Affairs "); category6.setCategoryDescription("Update yourself with current happening of world");
            Category category4= new Category(); category4.setCategoryId(204); category4.setCategoryTitle("Others"); category4.setCategoryDescription("Specific categories ");
           
	       
	       
	       
	        this.categoryRepo.save(category1); this.categoryRepo.save(category2);  this.categoryRepo.save(category3);  this.categoryRepo.save(category4); this.categoryRepo.save(category5); this.categoryRepo.save(category6);
	        
	
	    */    
	        
	        
	        
	       // END OF TEMPORARY CODE : ! time execution after creating database 
		
	        
	        
	        
	        
	    	
		    List<Role> roles = List.of(role, role1);
	        List<Role> result = this.roleRepo.saveAll(roles);
			result.forEach(r -> { System.out.println(r.getName());});
		
	        
	        
	        
	        
	        
	    
	        
	        

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

}


// H No 12/139 , Ward 12 , Sindhi Dharmshala  , Mukherji Nagar  ,   Raisen , Madhya Pradesh , Pin code - 464551