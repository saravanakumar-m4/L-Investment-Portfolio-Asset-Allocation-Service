package com.investment_portfolio_service.auth;

import lombok.Data;

@Data
public class AuthRequest {

	private String email;
	private String password;

}
