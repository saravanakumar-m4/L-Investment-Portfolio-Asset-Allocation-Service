package com.investment_portfolio_service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.investment_portfolio_service.user.User;
import com.investment_portfolio_service.user.UserRepository;
import com.investment_portfolio_service.user.UserService;

@SpringBootTest
class InvestmentPortfolioServiceApplicationTests {
	
	@InjectMocks
	UserService userService;

	@Mock
	UserRepository userRepo;

	@Test
	public void testCreateUser() {
		User u1 = new User(4, "sk", "sk@gmail.com", "sk@123", "HIGH");
		userService.save(u1);
		assertTrue("HIGH" == u1.getRiskprofile());
	}

}
