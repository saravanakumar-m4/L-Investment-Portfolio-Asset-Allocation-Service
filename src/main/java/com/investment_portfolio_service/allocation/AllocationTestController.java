package com.investment_portfolio_service.allocation;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//Test class -> not in prod
@RestController
@RequestMapping("/test/allocation")
public class AllocationTestController {

	private final AllocationService allocationService;

	public AllocationTestController(AllocationService allocationService) {
		this.allocationService = allocationService;
	}

	@GetMapping
	public Map<String, Double> test(@RequestParam String risk, @RequestParam double amount) {

		return allocationService.allocate(risk.toUpperCase(), amount);
	}
}
