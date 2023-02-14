package com.codewithdurgesh.blog.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter                     //lombok dependecy support these annotation, must be installed in the folder of STS (refer video)
@Setter
public class User implements UserDetails       //UserDetails is implemented directly , it helps in connecting with database and in BCryptPassword Encoder
	{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)           // AUTO / IDENTITY , use anyone can be used
	private int id;

	@Column(name = "user_name", nullable = false, length = 100)
	private String name;

	@Column(unique = true)               // To keep uses distinct
	private String email;

	private String password;

	private String about;

//MAPPING:::::::::::::::::::::::::	
	
	//IMP : Mapping need to be done correctly , otherwise problems in deleting due to constaints (refer old projects  SmartContactManager , ExamPortal , Videos  ) : // Currently user is not deleting due to constaints of role and post. ERROR
    	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Post> posts = new ArrayList<>();

		
	//@JoinTable is used to define properties of joined table. // Joined tables of  "User" and "Role" with their respective id columns
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "id"))
	private Set<Role> roles = new HashSet<>();

	
	// All these are the implemented methods of UserDetails interface , This is used to assign the Roles for granting access to roles based authentication, fetch out the each role from the HashSet of roles and make it object of SimpleGrantedAuthority.
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		List<SimpleGrantedAuthority> authories = this.roles.stream()
				.map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
		return authories;
	}


	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	
	@Override          //made this method , as our email is also the username here.
	public String getUsername() {
		return this.email;
	}


}
