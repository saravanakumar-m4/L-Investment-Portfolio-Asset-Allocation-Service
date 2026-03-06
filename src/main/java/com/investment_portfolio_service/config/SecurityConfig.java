package com.investment_portfolio_service.config;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import com.investment_portfolio_service.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final JwtUtil jwtUtil;

	public SecurityConfig(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			
				.authorizeHttpRequests(auth -> auth.requestMatchers("/auth/**", 
						"/createuser",
		                "/swagger-ui/**",
		                "/v3/api-docs/**",
		                "/v3/api-docs.yaml",
		                "/api/v1/assets" )
						.permitAll().anyRequest().authenticated())
				.addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public OncePerRequestFilter jwtFilter() {
		return new OncePerRequestFilter() {
					
			@Override
			protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
					FilterChain filterChain) throws ServletException, IOException {

				String authHeader = request.getHeader("Authorization");

				if (authHeader != null && authHeader.startsWith("Bearer ")) {
					String token = authHeader.substring(7);
					if (jwtUtil.validateToken(token)) {
						String username = jwtUtil.extractUsername(token);
						UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
								username, null, null);
						SecurityContextHolder.getContext().setAuthentication(authentication);
					}
				}
				filterChain.doFilter(request, response);
			}
		};
	}
}
