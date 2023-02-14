package com.codewithdurgesh.blog.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data                //It does the work of @getter,@setters , @NoArgsConstructor
public class Role {

	@Id	
	private int id;
	private String name;
	
}
