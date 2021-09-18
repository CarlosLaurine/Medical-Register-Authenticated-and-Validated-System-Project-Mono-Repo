package com.carloslaurinedev.medicalregisterapi.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController

@RequestMapping(value = "/gcb-medical-register-api/specialties")
@Api(value = "MedicalSpecialty Controller")
@CrossOrigin(origins = "{'/v2/api-docs', '/configuration/ui', 'swagger-resources/**', 'configuration/security', 'swagger-ui.html', 'webjars/**' }")

public class MedicalSpecialtyController {

	@Autowired
	private MedicalSpecialtyService service;

	@ApiOperation(value = "Returns all Preexistent MedicalSpecialties as DTOs with Pagination. Returns an HTTP Ok Response Status (200)")
	@GetMapping
	public ResponseEntity<Page<MedicalSpecialtyDTO>> selectAllPaged(Pageable pageable) {

		Page<MedicalSpecialtyDTO> currentPage = service.selectAllPaged(pageable);

		return ResponseEntity.ok().body(currentPage);
	}

	@ApiOperation(value = "Returns a Unique Preexistent MedicalSpecialty as a DTO through the Path Variable 'id'. Returns an HTTP Ok Response Status (200)")
	@GetMapping(value = "/{id}")
	public ResponseEntity<MedicalSpecialtyDTO> searchById(@PathVariable Long id) {

		MedicalSpecialtyDTO dto = service.searchById(id);

		return ResponseEntity.ok().body(dto);
	}

	@ApiOperation(value = "Persists a New MedicalSpecialty through a DTO at the Request Body and Returns a 'Created' HTML Status (201) along with the New User's URI at the Header")
	@PostMapping
	public ResponseEntity<MedicalSpecialtyDTO> insert(@RequestBody MedicalSpecialtyDTO dto) {

		dto = service.insert(dto);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();

		return ResponseEntity.created(uri).body(dto);
	}

	@ApiOperation(value = "Updates a Preexistent MedicalSpecialty at the Database through a DTO at the Request Body along with the Path Variable 'id' and Returns it as a DTO. Returns an HTTP Ok Response Status (200)")
	@PutMapping(value = "/{id}")
	public ResponseEntity<MedicalSpecialtyDTO> update(@PathVariable Long id, @RequestBody MedicalSpecialtyDTO dto) {

		dto = service.update(id, dto);

		return ResponseEntity.ok().body(dto);
	}

	@ApiOperation(value = "Performs a Soft Deletion Operation with a Preexistent MedicalSpecialty at the Database through the Path Variable Id, Updating the Object's Deleted Attribute Value to 'True' and Blocking its appearance on future Select/Search Queries. It does not Physically Delete it from the Database. Returns an HTTP No Content Response Status (204)")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> softDelete(@PathVariable Long id) {

		service.softDelete(id);

		return ResponseEntity.noContent().build();
	}

}
