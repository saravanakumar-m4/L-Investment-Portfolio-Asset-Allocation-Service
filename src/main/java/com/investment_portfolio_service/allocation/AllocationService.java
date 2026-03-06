package com.investment_portfolio_service.allocation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class AllocationService {

	public Map<String, Double> allocate(String riskProfile, double amount) {
		Map<String, Double> allocation = new HashMap<>();
		
		System.out.println("Risk profile received: " + riskProfile);

		switch (riskProfile) {
		case "LOW" -> {
			allocation.put("EQUITY", amount * 0.30);
			allocation.put("DEBT", amount * 0.50);
			allocation.put("GOLD", amount * 0.10);
			allocation.put("CASH", amount * 0.10);
		}
		case "HIGH" -> {
			allocation.put("EQUITY", amount * 0.70);
			allocation.put("DEBT", amount * 0.15);
			allocation.put("GOLD", amount * 0.10);
			allocation.put("CASH", amount * 0.05);
		}
		default -> {
			allocation.put("EQUITY", amount * 0.50);
			allocation.put("DEBT", amount * 0.30);
			allocation.put("GOLD", amount * 0.10);
			allocation.put("CASH", amount * 0.10);
		}
		
		}
		return allocation;

	}

}
