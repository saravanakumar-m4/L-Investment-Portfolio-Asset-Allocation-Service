package com.investment_portfolio_service.portfolio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/portfolios")
@Tag(name = "Portfolio Controller", description = "Portfolio Operation related APIs")
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {

        this.portfolioService = portfolioService;
    }

    @Operation(summary = "Create Portfolio", description = "Create Portfolio by user ID")
    @PostMapping("/{userId}")
    public Portfolio create(@PathVariable Long userId) {
        return portfolioService.createPortfolio(userId);
    }

    @Operation(summary = "Create Portfolio with asset", description = "Create Portfolio with invested amount and individual asset")
    @PostMapping("/{portfolioId}/assets/{assetId}")
    public Portfolio addAsset(@PathVariable Long portfolioId, @PathVariable Long assetId, @RequestParam double amount) {
        return portfolioService.addAsset(portfolioId, assetId, amount);
    }

    @Operation(summary = "Create Portfolio with allocation", description = "Create Portfolio with allocation logic - Based on user risk_profile")
    @PostMapping("/{userId}/allocate")
    public Portfolio createWithAllocation(@PathVariable Long userId, @RequestParam double amount) {
        return portfolioService.createPortfolioWithAllocation(userId, amount);
    }

    @Operation(summary = "Get user portfolio", description = "Retrieve user current portfolio")
    @GetMapping("/{userid}")
    public Optional<Portfolio> userPortfolio(@PathVariable Long userid) {
        return portfolioService.getPortfolio(userid);
    }


}
