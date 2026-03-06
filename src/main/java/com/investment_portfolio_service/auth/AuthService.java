package com.investment_portfolio_service.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.investment_portfolio_service.user.User;
import com.investment_portfolio_service.user.UserService;
import com.investment_portfolio_service.util.JwtUtil;

@Service
public class AuthService {

	private final UserService userService;
	private final JwtUtil jwtUtil;
	private final PasswordEncoder passwordEncoder;

	public AuthService(UserService userService, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.jwtUtil = jwtUtil;
		this.passwordEncoder = passwordEncoder;
	}

	public AuthResponse login(AuthRequest request) {
		User user = userService.findByEmail(request.getEmail())
				.orElseThrow(() -> new RuntimeException("Invalid credentials"));

		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
			throw new RuntimeException("Invalid credentials");
		}

		String token = jwtUtil.generateToken(user.getEmail());
		return new AuthResponse(token);
	}

}
