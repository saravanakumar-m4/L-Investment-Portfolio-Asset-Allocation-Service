package com.investment_portfolio_service.portfolio;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.investment_portfolio_service.asset.Asset;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PortfolioAsset {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JsonBackReference
	private Portfolio portfolio;

	@ManyToOne
	private Asset asset;

	private double investedAmount;

}
