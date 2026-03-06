package com.investment_portfolio_service.rebalance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.investment_portfolio_service.allocation.AllocationService;
import com.investment_portfolio_service.exception.ResourceNotFoundException;
import com.investment_portfolio_service.portfolio.Portfolio;
import com.investment_portfolio_service.portfolio.PortfolioAsset;
import com.investment_portfolio_service.portfolio.PortfolioRepository;
import com.investment_portfolio_service.user.User;

@Service
public class RebalanceService {

	private final PortfolioRepository portfolioRepository;
	private final AllocationService allocationService;

	public RebalanceService(PortfolioRepository portfolioRepository, AllocationService allocationService) {
		this.portfolioRepository = portfolioRepository;
		this.allocationService = allocationService;
	}

	public List<String> rebalance(Long portfolioId) {

		Portfolio portfolio = portfolioRepository.findById(portfolioId)
				.orElseThrow(() -> new ResourceNotFoundException("Portfolio not found"));

		User user = portfolio.getUser();
		double totalValue = portfolio.getTotalValue();

// 1️ CURRENT allocation

		Map<String, Double> currentAllocation = new HashMap<>();
		
		for (PortfolioAsset pa : portfolio.getAssets()) {

			String assetType = pa.getAsset().getName();
			currentAllocation.merge(assetType, pa.getInvestedAmount(), Double::sum);
			
		}
		
// 2️ TARGET allocation
		Map<String, Double> targetAllocation = allocationService.allocate(user.getRiskprofile(), totalValue);

// 3️ Compare & generate actions
		List<String> actions = new ArrayList<>();

		for (String assetType : targetAllocation.keySet()) {
			double current = currentAllocation.getOrDefault(assetType, 0.0);
			double target = targetAllocation.get(assetType);

			double diff = target - current;

// 5% threshold It avoids unnecessary small trades (avoid transactions cost).
			if (Math.abs(diff) > totalValue * 0.05) {
				if (diff > 0) {
					actions.add("BUY " + assetType + " worth " + (long) diff);
				} else {
					actions.add("SELL " + assetType + " worth " + (long) Math.abs(diff));
				}
			}
		}

		System.out.println("CURRENT ALLOCATION = " + currentAllocation);
		System.out.println("TARGET ALLOCATION = " + targetAllocation);

		return actions;
	}

}
