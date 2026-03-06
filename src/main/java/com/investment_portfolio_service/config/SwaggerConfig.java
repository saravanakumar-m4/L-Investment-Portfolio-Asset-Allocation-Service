package com.investment_portfolio_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI investmentOpenAPI() {

		final String securitySchemeName = "bearerAuth";

		return new OpenAPI().info(new Info()
				.title("Investment Portfolio & Asset Allocation API")
				.description(
				"A Spring Boot–based backend service that builds, manages, allocates, and rebalances an investment portfolio based on user risk profile and investment amount. This project simulates real-world portfolio management logic used in fintech and wealth-management platforms.")
				.version("1.0.0"))
				.addSecurityItem(new SecurityRequirement()
						.addList(securitySchemeName))
				.components(new io.swagger.v3.oas.models.Components().addSecuritySchemes(securitySchemeName,
						new SecurityScheme().name(securitySchemeName).type(SecurityScheme.Type.HTTP).scheme("bearer")
								.bearerFormat("JWT")));
	}

}
