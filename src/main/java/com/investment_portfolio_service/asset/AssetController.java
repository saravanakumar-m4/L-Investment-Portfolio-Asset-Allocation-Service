package com.investment_portfolio_service.asset;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/assets")
@Tag(name = "Asset Controller", description = "Asset related APIs")
public class AssetController {

	private AssetService assetService;

	public AssetController(AssetService assetService) {
		super();
		this.assetService = assetService;
	}

	@PostMapping
	@Operation(summary = "Create Asset", description = "Create assest in db")
	public Asset create(@RequestBody Asset asset) {
		return assetService.save(asset);
	}

	@GetMapping
	@Operation(summary = "Get Asset", description = "Fetch all asset details")
	public List<Asset> list() {
		return assetService.findAll();
	}

}