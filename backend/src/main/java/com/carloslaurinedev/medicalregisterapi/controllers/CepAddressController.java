package com.carloslaurinedev.medicalregisterapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.carloslaurinedev.medicalregisterapi.dtos.ViaCepAddressDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

//This Controller was Implemented with the main purpose of Testing future Address APIs and their URL Requests before Implementing it at MedicalRegisterController Class. This is a Maintenance-Designed Endpoint destined to soften the future Development process at this Source-Code

@RestController
@RequestMapping(path = "/gcb-medical-register-api/viacep")

@Api(value = "CEP API Controller")
@CrossOrigin(origins = "{'/v2/api-docs', '/configuration/ui', 'swagger-resources/**', 'configuration/security', 'swagger-ui.html', 'webjars/**' }")

public class CepAddressController {

	@ApiOperation(value = "Builds a ViaCEP API Formatted URL with the CEP Path Variable passed at the Request, then performs a JSON HTTP Request with RestTemplate to the ViaCEP API and Parses its Response into a ViaCepAddressDTO Type Object, then Returns it. Returns an HTTP Ok Response Status (200)")
	@GetMapping("/{cep}")
	public ResponseEntity<Object> searchAddressByCEP(@PathVariable String cep) {

		String url = "https://viacep.com.br/ws/" + cep + "/json/";

		RestTemplate restTemplate = new RestTemplate();

		ViaCepAddressDTO address = restTemplate.getForObject(url, ViaCepAddressDTO.class);

		return ResponseEntity.ok().body(address);
	}
}
