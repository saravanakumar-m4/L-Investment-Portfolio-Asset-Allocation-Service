package com.investment_portfolio_service.portfolio;

import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.investment_portfolio_service.allocation.AllocationService;
import com.investment_portfolio_service.asset.Asset;
import com.investment_portfolio_service.asset.AssetRepository;
import com.investment_portfolio_service.exception.ResourceNotFoundException;
import com.investment_portfolio_service.user.User;
import com.investment_portfolio_service.user.UserRepository;
import com.investment_portfolio_service.portfolio.PortfolioRepository;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;
    private final AssetRepository assetRepository;
    private final PortfolioAssetRepository portfolioAssetRepository;
    private final AllocationService allocationService;

    public PortfolioService(PortfolioRepository portfolioRepository, UserRepository userRepository,
                            AssetRepository assetRepository, PortfolioAssetRepository portfolioAssetRepository,
                            AllocationService allocationService) {

        this.portfolioRepository = portfolioRepository;
        this.userRepository = userRepository;
        this.assetRepository = assetRepository;
        this.portfolioAssetRepository = portfolioAssetRepository;
        this.allocationService = allocationService;
    }

    public Portfolio createPortfolio(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Portfolio portfolio = new Portfolio();
        portfolio.setUser(user);
        portfolio.setTotalValue(0);
        return portfolioRepository.save(portfolio);
    }

    public Portfolio addAsset(Long portfolioId, Long assetId, double amount) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio not found"));

        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new ResourceNotFoundException("Asset not found"));

        PortfolioAsset pa = new PortfolioAsset();
        pa.setPortfolio(portfolio);
        pa.setAsset(asset);
        pa.setInvestedAmount(amount);

        portfolioAssetRepository.save(pa);
        portfolio.setTotalValue(portfolio.getTotalValue() + amount);
        return portfolioRepository.save(portfolio);
    }

    public Portfolio createPortfolioWithAllocation(Long userId, double amount) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Portfolio portfolio = new Portfolio();
        portfolio.setUser(user);
        portfolio.setTotalValue(amount);
        portfolio = portfolioRepository.save(portfolio);

//  ALLOCATION
        Map<String, Double> allocation =
                allocationService.allocate(user.getRiskprofile(), amount);

        System.out.println("Risk Profile: " + user.getRiskprofile());

        for (Map.Entry<String, Double> entry : allocation.entrySet()) {

            Asset asset = assetRepository.findAll()
                    .stream()
                    .filter(a -> a.getName().equals(entry.getKey()))
                    .findFirst()
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Asset not found: " + entry.getKey()));

            PortfolioAsset pa = new PortfolioAsset();
            pa.setPortfolio(portfolio);
            pa.setAsset(asset);
            pa.setInvestedAmount(entry.getValue());


            portfolioAssetRepository.save(pa);
            portfolio.getAssets().add(pa); // this get asset data

        }

        return portfolio;
    }

    // to get userPortfolio
    public Optional<Portfolio> getPortfolio(Long id) {
        return portfolioRepository.findById(id);

    }


}
