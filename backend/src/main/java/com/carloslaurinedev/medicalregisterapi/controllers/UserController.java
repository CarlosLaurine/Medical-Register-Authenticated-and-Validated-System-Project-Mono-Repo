package com.carloslaurinedev.medicalregisterapi.controllers;

import java.net.URI;

import javax.validation.Valid;

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

import com.carloslaurinedev.medicalregisterapi.dtos.UserDTO;
import com.carloslaurinedev.medicalregisterapi.dtos.UserInsertDTO;
import com.carloslaurinedev.medicalregisterapi.dtos.UserUpdateDTO;
import com.carloslaurinedev.medicalregisterapi.services.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController

@RequestMapping(value = "/gcb-medical-register-api/users")

@Api(value = "User Controller")
@CrossOrigin(origins = "{'/v2/api-docs', '/configuration/ui', 'swagger-resources/**', 'configuration/security', 'swagger-ui.html', 'webjars/**' }")

public class UserController {

	@Autowired
	private UserService service;

	@ApiOperation(value = "Returns all Preexistent Users as DTOs with Pagination. Returns an HTTP Ok Response Status (200)")
	@GetMapping
	public ResponseEntity<Page<UserDTO>> selectAllPaged(Pageable pageable) {

		// Spring Standard Pageable Request Parameters - "page","size" and "sort"

		Page<UserDTO> currentPage = service.selectAllPaged(pageable);

		return ResponseEntity.ok().body(currentPage);
	}

	@ApiOperation(value = "Returns a Unique Preexistent User as a DTO through the Path Variable Id. Returns an HTTP Ok Response Status (200)")
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> searchById(@PathVariable Long id) {

		UserDTO dto = service.searchById(id);

		return ResponseEntity.ok().body(dto);
	}

	@ApiOperation(value = "Persists a New User through a DTO and Returns a 'Created' HTML Status (201) along with the New User's URI at the Header")
	@PostMapping
	public ResponseEntity<UserDTO> insert(@Valid @RequestBody UserInsertDTO dto) {

		UserDTO newDto = service.insert(dto);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newDto.getId()).toUri();

		return ResponseEntity.created(uri).body(newDto);
	}

	@ApiOperation(value = "Updates a Preexistent User at the Database through a DTO at the Request Body along with the Path Variable 'id' and Returns it as a DTO. Returns an HTTP Ok Response Status (200)")
	@PutMapping(value = "/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO dto) {

		UserDTO newDto = service.update(id, dto);

		return ResponseEntity.ok().body(newDto);
	}

	@ApiOperation(value = "Deletes a Preexistent User through the PathVariable Id. Returns an HTTP No Content Response Status (204)")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {

		service.delete(id);

		return ResponseEntity.noContent().build();
	}

}
