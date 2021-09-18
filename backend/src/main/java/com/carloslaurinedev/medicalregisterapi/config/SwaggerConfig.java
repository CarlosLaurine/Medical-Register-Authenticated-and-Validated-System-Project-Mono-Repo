package com.carloslaurinedev.medicalregisterapi.config;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	//Setting Property Value to Define future Prod Hosts:
	
	@Value("${gcbregisterapi.swagger.path}")
	private String swaggerPath;
	
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				//.host(swaggerPath)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.carloslaurinedev.medicalregisterapi"))
				.paths(regex("/gcb-medical-register-api/.*"))
				.build()
				.apiInfo(metaInfo());
	}

	@SuppressWarnings("rawtypes")
	private ApiInfo metaInfo() {

		ApiInfo apiInfo = new ApiInfo(
				"GCB Group Medical Register REST API", 
				"Development Project for the GCB Group Technical Test", 
				"1.0", 
				"Terms of Service",
				
				new Contact(
						"Carlos Laurine",
						"https://github.com/CarlosLaurine/Medical-Register-Authenticated-and-Validated-System-Project-Mono-Repo",
						"carlos.pinho@ucsal.edu.br"),
				
				"GNU Affero General Public License v3.0",
				"https://www.gnu.org/licenses/agpl-3.0.pt-br.html",
				new ArrayList<VendorExtension>());

		return apiInfo;
	}

}