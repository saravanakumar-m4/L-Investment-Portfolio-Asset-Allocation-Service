package com.investment_portfolio_service.portfolio;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioAssetRepository extends JpaRepository<PortfolioAsset, Long> {

	
}
