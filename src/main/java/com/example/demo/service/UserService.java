package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.model.User;

@Service
public class UserService {

	
	public User add(User user) {
		user.setAge(100);
		user.setName("tom");
		return user;
	}
}