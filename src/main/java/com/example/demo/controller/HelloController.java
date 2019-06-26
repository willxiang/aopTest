package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.aspect.annotation.Log;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

@Log(logStr = "HelloController")
@RestController
public class HelloController {

	@Autowired
	UserService userService;

	@RequestMapping("/hi")
	public String Hi(User user) {

		userService.add(user);

		return "hi" + System.currentTimeMillis();
	}

	@RequestMapping("/update")
	public String update(User user) {
		return "update" + System.currentTimeMillis();
	}
	
	@RequestMapping("/up")
	public String update1(User user) {
		return "update " + System.currentTimeMillis();
	}

}
