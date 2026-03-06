package com.investment_portfolio_service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "User Controller", description = "User related APIs")

public class UserController {
//Testing purpose - create user & list users	

	@Autowired
	private UserService userService;

	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Operation(summary = "Create user", description = "Create user in db")
	@PostMapping("createuser")
	public User createUser(@RequestBody User user) {
		User u1 = new User();

		u1.setName(user.getName());
		u1.setEmail(user.getEmail());
		u1.setRiskprofile(user.getRiskprofile().toUpperCase());
		u1.setPassword(passwordEncoder().encode(user.getPassword()));

		userService.save(u1);
		return u1;
	}

	@Operation(summary = "Get users", description = "Fetch all user details")
	@GetMapping("userlist")
	public List<User> userList() {
		return userService.userList();
	}

}
