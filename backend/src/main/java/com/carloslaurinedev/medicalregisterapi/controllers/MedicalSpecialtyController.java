package com.carloslaurinedev.medicalregisterapi.controllers;

import java.net.URI;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.carloslaurinedev.medicalregisterapi.dtos.MedicalSpecialtyDTO;
import com.carloslaurinedev.medicalregisterapi.services.MedicalSpecialtyService;

@RestController

@RequestMapping(value = "/specialties")

public class MedicalSpecialtyController {

	@Autowired
	private MedicalSpecialtyService service;

	@GetMapping
	public ResponseEntity<Page<MedicalSpecialtyDTO>> findAll(Pageable pageable) {

		Page<MedicalSpecialtyDTO> currentPage = service.findAllPaged(pageable);

		return ResponseEntity.ok().body(currentPage);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<MedicalSpecialtyDTO> findById(@PathVariable Long id) {

		MedicalSpecialtyDTO dto = service.findById(id);

		return ResponseEntity.ok().body(dto);
	}

	@PostMapping
	public ResponseEntity<MedicalSpecialtyDTO> insert(@RequestBody MedicalSpecialtyDTO dto) {

		dto = service.insert(dto);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();

		return ResponseEntity.created(uri).body(dto);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<MedicalSpecialtyDTO> update(@PathVariable Long id, @RequestBody MedicalSpecialtyDTO dto) {

		dto = service.update(id, dto);

		return ResponseEntity.ok().body(dto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {

		service.delete(id);

		return ResponseEntity.noContent().build();
	}

}
