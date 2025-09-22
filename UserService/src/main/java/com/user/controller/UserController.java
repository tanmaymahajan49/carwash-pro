package com.user.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.model.User;
import com.user.model.UserDto;
import com.user.service.UserService;

@RestController
@RequestMapping("/user")
//@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/allUsers")
	public ResponseEntity<List<User>> getAllUsers() {
		return userService.getAllUsers();
	}

	@PostMapping("/customer-register")
	public ResponseEntity<String> registerFarmer(@RequestBody UserDto userDto) {
		return userService.registerFarmer(userDto);
	}

	@PostMapping("/washer-register")
	public ResponseEntity<String> registerDealer(@RequestBody UserDto userDto) {
		return userService.registerDealer(userDto);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody User user) {
		return userService.login(user);
	}

}
