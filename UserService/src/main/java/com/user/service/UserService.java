package com.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.user.feign.UserDealerInterface;
import com.user.feign.UserFarmerInterface;
import com.user.model.User;
import com.user.model.UserDto;
import com.user.repo.UserRepo;

@Service
//@CrossOrigin(origins = "http://localhost:4200")
public class UserService {

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

	@Autowired
	UserRepo userRepo;

	@Autowired
	UserFarmerInterface farmer_service;

	@Autowired
	UserDealerInterface dealer_service;
	@Autowired
	private JwtService jwtService;

	@Autowired
	AuthenticationManager authManager;

	public ResponseEntity<String> registerFarmer(UserDto userDto) {
		Optional<User> existingUser = Optional.ofNullable(userRepo.findByEmail(userDto.getEmail()));

		if (existingUser.isPresent()) {
			return new ResponseEntity<>("Registration failed: A user with email \"" + userDto.getEmail()
					+ "\" already exists.\nPlease use a different email address.", HttpStatus.CONFLICT);
		}

		User user = new User(userDto.getEmail(), encoder.encode(userDto.getPassword()), "CUSTOMER");
		User saved = userRepo.save(user);

		if (saved == null) {
			return new ResponseEntity<>("User registration failed due to a server error.",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return farmer_service.registerFarmer(userDto);
	}

	public ResponseEntity<String> registerDealer(UserDto userDto) {
		Optional<User> existingUser = Optional.ofNullable(userRepo.findByEmail(userDto.getEmail()));
		if (existingUser.isPresent()) {
			return new ResponseEntity<>("Registration failed: A user with email \"" + userDto.getEmail()
					+ "\" already exists.\nPlease use a different email address.", HttpStatus.CONFLICT);
		}

		User user = new User(userDto.getEmail(), encoder.encode(userDto.getPassword()), "WASHER");

		User saved = userRepo.save(user);
		if (saved == null) {
			return new ResponseEntity<>("User registration failed due to a server error.",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return dealer_service.registerDealer(userDto);
	}

	public ResponseEntity<Map<String, String>> login(User loginUser) {
		Authentication authentication = authManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getEmail(), loginUser.getPassword()));
		if (authentication.isAuthenticated()) {
			User user = userRepo.findByEmail(loginUser.getEmail());
			System.out.println("User - " +user);
			String token = jwtService.generateToken(user);
			Map<String, String> response = new HashMap<>();
			response.put("token", token);
			System.out.println("Response - " + response);
			System.out.println("Token - " + token);
			return ResponseEntity.ok(response);
		} else {
			throw new UsernameNotFoundException("Invalid user request!");
		}
	}

	public ResponseEntity<List<User>> getAllUsers() {
		try {
			return new ResponseEntity<>(userRepo.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>(new ArrayList<>(), HttpStatus.NOT_FOUND);
	}

}
