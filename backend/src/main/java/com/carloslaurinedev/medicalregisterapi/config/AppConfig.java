package com.carloslaurinedev.medicalregisterapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import com.carloslaurinedev.medicalregisterapi.services.DataBaseService;

@SuppressWarnings("deprecation")
@Configuration
@Profile("dev")
public class AppConfig {

	@Autowired
	private DataBaseService mySqlService;

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
		tokenConverter.setSigningKey(jwtSecret);
		return tokenConverter;
	}

	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}

	@Bean
	public boolean instantiateDatabase() {
		if (!"update".equals(strategy)) {
			return false;
		}
		mySqlService.instantiateTestDatabase();
		return true;
	}

}
