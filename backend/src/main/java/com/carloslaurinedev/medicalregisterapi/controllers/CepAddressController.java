package com.carloslaurinedev.medicalregisterapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.carloslaurinedev.medicalregisterapi.dtos.ViaCepAddressDTO;

@RestController
@RequestMapping(path = "/viacep")
public class CepAddressController {

	@GetMapping("/{cep}")
	public ResponseEntity<Object> getAddress(@PathVariable String cep) {

		String url = "https://viacep.com.br/ws/" + cep + "/json/";

		RestTemplate restTemplate = new RestTemplate();

		ViaCepAddressDTO address = restTemplate.getForObject(url, ViaCepAddressDTO.class);

		return ResponseEntity.ok().body(address);
	}
}
