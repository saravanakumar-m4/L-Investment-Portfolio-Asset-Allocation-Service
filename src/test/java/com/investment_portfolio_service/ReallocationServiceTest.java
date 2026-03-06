package com.investment_portfolio_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.investment_portfolio_service.allocation.AllocationService;
import com.investment_portfolio_service.asset.Asset;
import com.investment_portfolio_service.portfolio.Portfolio;
import com.investment_portfolio_service.portfolio.PortfolioAsset;
import com.investment_portfolio_service.portfolio.PortfolioRepository;
import com.investment_portfolio_service.rebalance.RebalanceService;
import com.investment_portfolio_service.user.User;

@ExtendWith(MockitoExtension.class)
class ReallocationServiceTest {

	@InjectMocks
	RebalanceService rebalanceService;

	@Mock
	PortfolioRepository portfolioRepository;

	@Mock
	AllocationService allocationService;

	@Test
	public void rebalanceTestWithHighProfile() {
		User u1 = new User();
		u1.setRiskprofile("HIGH");

		Asset equity = new Asset();
		equity.setName("EQUITY");

		Asset debt = new Asset();
		debt.setName("DEBT");

		PortfolioAsset p1 = new PortfolioAsset();
		p1.setAsset(equity);
		p1.setInvestedAmount(10000);

		PortfolioAsset p2 = new PortfolioAsset();
		p2.setAsset(debt);
		p2.setInvestedAmount(20000);

		Portfolio portfolio1 = new Portfolio();
		portfolio1.setId(1L);
		portfolio1.setUser(u1);
		portfolio1.setTotalValue(30000);
		portfolio1.setAssets(List.of(p1, p2));

		when(portfolioRepository.findById(1L)).thenReturn(Optional.of(portfolio1));

		when(allocationService.allocate("HIGH", 30000.0)).thenReturn(Map.of("EQUITY", 21000.0, "DEBT", 9000.0));

		List<String> actions = rebalanceService.rebalance(1L);

		assertEquals(2, actions.size());
		assertTrue(actions.contains("BUY EQUITY worth 11000"));
		assertTrue(actions.contains("SELL DEBT worth 11000"));
	}

	@Test
	public void rebalanceTestWithLowProfile_Threshold() {
		User u1 = new User();
		u1.setRiskprofile("LOW");

		Asset gold = new Asset();
		gold.setName("GOLD");
		Asset cash = new Asset();
		cash.setName("CASH");

		PortfolioAsset p1 = new PortfolioAsset();
		p1.setAsset(gold);
		p1.setInvestedAmount(750.0);

		PortfolioAsset p2 = new PortfolioAsset();
		p2.setAsset(cash);
		p2.setInvestedAmount(750.0);

		Portfolio portfolio = new Portfolio();
		portfolio.setId(1L);
		portfolio.setUser(u1);
		portfolio.setAssets(List.of(p1, p2));
		portfolio.setTotalValue(5000);

		when(portfolioRepository.findById(1l)).thenReturn(Optional.of(portfolio));

		when(allocationService.allocate("LOW", 5000.0)).thenReturn(Map.of("GOLD", 1000.0, "CASH", 1000.0));

		List<String> result = rebalanceService.rebalance(1l);

		assertTrue(result.isEmpty());

	}

}