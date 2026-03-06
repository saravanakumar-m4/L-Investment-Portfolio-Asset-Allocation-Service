package com.investment_portfolio_service.auth;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth Controller", description = "Generate JWT token")
public class AuthController {

	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@Operation(summary = "Login method", description = "Get user login credentials and return JWT token")
	@PostMapping("/login")
	public AuthResponse login(@RequestBody AuthRequest request) {
		return authService.login(request);
	}

}
