package com.investment_portfolio_service.rebalance;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/rebalance")
@Tag(name = "Rebalance Controller", description = "Reallocate user portfolio")
public class RebalanceController {

	private final RebalanceService rebalanceService;

	public RebalanceController(RebalanceService rebalanceService) {
		this.rebalanceService = rebalanceService;
	}

	@GetMapping("/{portfolioId}")
	@Operation(summary = "Rebalance", description = "Reallocate user existing portfolio based on user risk profile")
	public List<String> rebalance(@PathVariable Long portfolioId) {
		return rebalanceService.rebalance(portfolioId);
	}

}
