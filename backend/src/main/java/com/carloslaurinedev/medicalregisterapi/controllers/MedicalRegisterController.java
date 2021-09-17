package com.carloslaurinedev.medicalregisterapi.controllers;

import java.net.URI;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.carloslaurinedev.medicalregisterapi.dtos.MedicalRegisterDTO;
import com.carloslaurinedev.medicalregisterapi.services.MedicalRegisterService;

@RestController

@RequestMapping(value = "/doctors")

public class MedicalRegisterController {

	@Autowired
	private MedicalRegisterService service;

	@GetMapping
	public ResponseEntity<Page<MedicalRegisterDTO>> findAll(Pageable pageable,
			@RequestParam(value = "specialtyId", defaultValue = "0") Long specialtyId,
			@RequestParam(value = "name", defaultValue = "") String name) {

		// Spring Standard Pageable Request Parameters - "page","size" and "sort"

		Page<MedicalRegisterDTO> currentPage = service.findAllPaged(pageable, specialtyId, name.trim());

		return ResponseEntity.ok().body(currentPage);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<MedicalRegisterDTO> findById(@PathVariable Long id) {

		MedicalRegisterDTO dto = service.findById(id);

		return ResponseEntity.ok().body(dto);
	}

	@PostMapping
	public ResponseEntity<MedicalRegisterDTO> insert(@Valid @RequestBody MedicalRegisterDTO dto) {

		dto = service.insert(dto);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();

		return ResponseEntity.created(uri).body(dto);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<MedicalRegisterDTO> update(@PathVariable Long id,
			@Valid @RequestBody MedicalRegisterDTO dto) {

		dto = service.update(id, dto);

		return ResponseEntity.ok().body(dto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {

		service.delete(id);

		return ResponseEntity.noContent().build();
	}

}
