package com.example.ezyroulettehttpserver.entity;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("user")
public class User {
	@Id
	Long id;
	
	String username;
	String password;
	String loginToken;
	float balance;
}
