package com.investment_portfolio_service.asset;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class AssetService {

	private AssetRepository assetRepository;

	public AssetService(AssetRepository assetRepository) {
		this.assetRepository = assetRepository;
	}

	public Asset save(Asset asset) {
		return assetRepository.save(asset);
	}

	public List<Asset> findAll() {
		return assetRepository.findAll();
	}

}
