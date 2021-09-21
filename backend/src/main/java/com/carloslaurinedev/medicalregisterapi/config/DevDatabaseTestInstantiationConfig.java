package com.carloslaurinedev.medicalregisterapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.carloslaurinedev.medicalregisterapi.services.DataBaseService;

@Configuration
@Profile("dev")
public class DevDatabaseTestInstantiationConfig {

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;

	@Autowired
	private DataBaseService mySqlService;

	@Bean
	public boolean instantiateDatabase() {
		if ("none".equals(strategy)) {
			return false;
		}
		mySqlService.instantiateTestDatabase();
		return true;
	}

}
