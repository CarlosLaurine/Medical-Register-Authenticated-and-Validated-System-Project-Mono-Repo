package com.carloslaurinedev.medicalregisterapi.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.carloslaurinedev.medicalregisterapi.dtos.MedicalRegisterDTO;
import com.carloslaurinedev.medicalregisterapi.dtos.MedicalRegisterWithAddressDTO;
import com.carloslaurinedev.medicalregisterapi.dtos.ViaCepAddressDTO;
import com.carloslaurinedev.medicalregisterapi.services.MedicalRegisterService;

@RestController

@RequestMapping(value = "/doctors")

public class MedicalRegisterController {

	@Autowired
	private MedicalRegisterService service;

	@GetMapping
	public ResponseEntity<Page<MedicalRegisterDTO>> selectAll(Pageable pageable,
			@RequestParam(value = "specialtyId", defaultValue = "0") Long specialtyId,
			@RequestParam(value = "name", defaultValue = "") String name) {

		// Spring Standard Pageable Request Parameters - "page","size" and "sort"

		Page<MedicalRegisterDTO> currentPage = service.selectAllPaged(pageable, specialtyId, name.trim());

		return ResponseEntity.ok().body(currentPage);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<MedicalRegisterDTO> searchById(@PathVariable Long id) {

		MedicalRegisterDTO dto = service.searchById(id);

		return ResponseEntity.ok().body(dto);
	}

	@GetMapping(value = "/with-address/{id}")
	public ResponseEntity<MedicalRegisterWithAddressDTO> searchWithAddressById(@PathVariable Long id) {

		MedicalRegisterDTO dto = service.searchById(id);

		String url = "https://viacep.com.br/ws/" + dto.getCep() + "/json/";

		RestTemplate restTemplate = new RestTemplate();

		ViaCepAddressDTO address = restTemplate.getForObject(url, ViaCepAddressDTO.class);

		MedicalRegisterWithAddressDTO fullDisplayDTO = new MedicalRegisterWithAddressDTO(dto, address);

		return ResponseEntity.ok().body(fullDisplayDTO);
	}

	@GetMapping(value = "/name/{name}")
	public ResponseEntity<List<MedicalRegisterDTO>> searchByName(@PathVariable String name) {

		List<MedicalRegisterDTO> dto = service.searchByName(name);

		return ResponseEntity.ok().body(dto);
	}

	@GetMapping(value = "/crm/{crm}")
	public ResponseEntity<MedicalRegisterDTO> searchByCrm(@PathVariable Integer crm) {

		MedicalRegisterDTO dto = service.searchByCrm(crm);

		return ResponseEntity.ok().body(dto);
	}

	@GetMapping(value = "/landline-phone/{landlinePhone}")
	public ResponseEntity<List<MedicalRegisterDTO>> searchByLandlinePhone(@PathVariable Long landlinePhone) {

		List<MedicalRegisterDTO> dto = service.searchByLandlinePhone(landlinePhone);

		return ResponseEntity.ok().body(dto);
	}

	@GetMapping(value = "/cellphone/{cellPhone}")
	public ResponseEntity<MedicalRegisterDTO> searchByCellPhone(@PathVariable Long cellPhone) {

		MedicalRegisterDTO dto = service.searchByCellphone(cellPhone);

		return ResponseEntity.ok().body(dto);
	}

	@GetMapping(value = "/cep/{cep}")
	public ResponseEntity<List<MedicalRegisterDTO>> searchByCep(@PathVariable Integer cep) {

		List<MedicalRegisterDTO> dto = service.searchByCep(cep);

		return ResponseEntity.ok().body(dto);
	}

	@PostMapping
	public ResponseEntity<MedicalRegisterWithAddressDTO> insert(@Valid @RequestBody MedicalRegisterDTO dto) {

		dto = service.insert(dto);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();

		String url = "https://viacep.com.br/ws/" + dto.getCep() + "/json/";

		RestTemplate restTemplate = new RestTemplate();

		ViaCepAddressDTO address = restTemplate.getForObject(url, ViaCepAddressDTO.class);

		MedicalRegisterWithAddressDTO fullDisplayDTO = new MedicalRegisterWithAddressDTO(dto, address);

		return ResponseEntity.created(uri).body(fullDisplayDTO);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<MedicalRegisterDTO> update(@PathVariable Long id,
			@Valid @RequestBody MedicalRegisterDTO dto) {

		dto = service.update(id, dto);

		return ResponseEntity.ok().body(dto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> softDelete(@PathVariable Long id) {

		service.softDelete(id);

		return ResponseEntity.noContent().build();
	}

}
