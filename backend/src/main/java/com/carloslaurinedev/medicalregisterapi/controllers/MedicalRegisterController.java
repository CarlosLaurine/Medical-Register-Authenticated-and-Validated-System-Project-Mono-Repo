package com.carloslaurinedev.medicalregisterapi.controllers;

import java.net.URI;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.carloslaurinedev.medicalregisterapi.dtos.MedicalRegisterDTO;
import com.carloslaurinedev.medicalregisterapi.dtos.MedicalRegisterWithAddressDTO;
import com.carloslaurinedev.medicalregisterapi.dtos.ViaCepAddressDTO;
import com.carloslaurinedev.medicalregisterapi.services.MedicalRegisterService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController

@RequestMapping(value = "/gcb-medical-register-api/doctors")

@Api(value = "MedicalRegister Controller")
@CrossOrigin(origins = "{'/v2/api-docs', '/configuration/ui', 'swagger-resources/**', 'configuration/security', 'swagger-ui.html', 'webjars/**' }")

public class MedicalRegisterController {

	@Autowired
	private MedicalRegisterService service;

	@ApiOperation(value = "Returns all Preexistent MedicalRegisters Paginated, and can Optionally Return all MedicalRegisters attached (Join Table) to a certain SpecialtyId Request Parameter 'specialtyId' Paginated. It can also return all MedicalRegisters containing the same Name Attribute as the optional RequestParameter 'name'. Finally, since both parameters are equally optional, this Endpoint can return all MedicalRegisters whose respective Attributes match those both RequestParemeters. Returns an HTTP Ok Response Status (200)")
	@GetMapping
	public ResponseEntity<Page<MedicalRegisterDTO>> selectAllPaged(Pageable pageable,
			@RequestParam(value = "specialtyId", defaultValue = "0") Long specialtyId,
			@RequestParam(value = "name", defaultValue = "") String name) {

		// Spring Standard Pageable Request Parameters - "page","size" and "sort"

		Page<MedicalRegisterDTO> currentPage = service.selectAllPaged(pageable, specialtyId, name.trim());

		return ResponseEntity.ok().body(currentPage);
	}

	@ApiOperation(value = "Returns a Unique Preexistent MedicalRegister as a DTO through the Path Varible 'id'. Returns an HTTP Ok Response Status (200)")
	@GetMapping(value = "/{id}")
	public ResponseEntity<MedicalRegisterDTO> searchById(@PathVariable Long id) {

		MedicalRegisterDTO dto = service.searchById(id);

		return ResponseEntity.ok().body(dto);
	}

	@ApiOperation(value = "Returns a Unique Preexistent MedicalSpecialty as a MedicalRegisterWithAddressDTO through its Id Attribute. This Special DTO Extends the former MedicalRegisterDTO while also Including all the Address Attributes Returned from an Internal JSON HTTP Request to ViaCEP API with the DTO's CEP Attribute (Obtained through Path Variable 'id'). Returns an HTTP Ok Response Status (200)")
	@GetMapping(value = "/with-address/{id}")
	public ResponseEntity<MedicalRegisterWithAddressDTO> searchWithAddressById(@PathVariable Long id) {

		MedicalRegisterDTO dto = service.searchById(id);

		String url = "https://viacep.com.br/ws/" + dto.getCep() + "/json/";

		RestTemplate restTemplate = new RestTemplate();

		ViaCepAddressDTO address = restTemplate.getForObject(url, ViaCepAddressDTO.class);

		MedicalRegisterWithAddressDTO fullDisplayDTO = new MedicalRegisterWithAddressDTO(dto, address);

		return ResponseEntity.ok().body(fullDisplayDTO);
	}

	@ApiOperation(value = "Returns a List of Preexistent MedicalRegisters as a List<DTO> Collection through the Path Varible 'name'. Returns an HTTP Ok Response Status (200)")
	@GetMapping(value = "/name/{name}")
	public ResponseEntity<List<MedicalRegisterDTO>> searchByName(@PathVariable String name) {

		List<MedicalRegisterDTO> dto = service.searchByName(name);

		return ResponseEntity.ok().body(dto);
	}

	@ApiOperation(value = "Returns a Unique Preexistent MedicalRegister as a DTO through the Path Varible 'crm'. Returns an HTTP Ok Response Status (200)")

	@GetMapping(value = "/crm/{crm}")
	public ResponseEntity<MedicalRegisterDTO> searchByCrm(@PathVariable Integer crm) {

		MedicalRegisterDTO dto = service.searchByCrm(crm);

		return ResponseEntity.ok().body(dto);
	}

	@ApiOperation(value = "Returns a List of Preexistent MedicalRegisters as a List<DTO> Collection through the Path Varible 'landlinePhone'. Returns an HTTP Ok Response Status (200)")

	@GetMapping(value = "/landline-phone/{landlinePhone}")
	public ResponseEntity<List<MedicalRegisterDTO>> searchByLandlinePhone(@PathVariable Long landlinePhone) {

		List<MedicalRegisterDTO> dto = service.searchByLandlinePhone(landlinePhone);

		return ResponseEntity.ok().body(dto);
	}

	@ApiOperation(value = "Returns a Unique Preexistent MedicalRegister as a DTO through the Path Varible 'cellPhone'. Returns an HTTP Ok Response Status (200)")

	@GetMapping(value = "/cellphone/{cellPhone}")
	public ResponseEntity<MedicalRegisterDTO> searchByCellPhone(@PathVariable Long cellPhone) {

		MedicalRegisterDTO dto = service.searchByCellphone(cellPhone);

		return ResponseEntity.ok().body(dto);
	}

	@ApiOperation(value = "Returns a List of Preexistent MedicalRegisters as a List<DTO> Collection through the Path Varible 'cep'. Returns an HTTP Ok Response Status (200)")

	@GetMapping(value = "/cep/{cep}")
	public ResponseEntity<List<MedicalRegisterDTO>> searchByCep(@PathVariable Integer cep) {

		List<MedicalRegisterDTO> dto = service.searchByCep(cep);

		return ResponseEntity.ok().body(dto);
	}

	@ApiOperation(value = "Persists a New MedicalRegister at the Database through a DTO at the Request Body and Returns a MedicalRegisterWithAddressDTO through the Path Variable 'id'. This Special DTO Extends the former MedicalRegisterDTO while also Including all the Address Attributes Returned from an Internal JSON HTTP Request to ViaCEP API with the DTO's CEP Attribute (Obtained through Path Variable 'id'). It also returns a 'Created' HTML Status (201) along with the New User's URI at the Header")

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

	@ApiOperation(value = "Updates a Preexistent MedicalRegister at the Database through a DTO at the Request Body along with the Path Variable 'id' and Returns a MedicalRegisterWithAddressDTO through the Path Variable 'id'. This Special DTO Extends the former MedicalRegisterDTO while also Including all the Address Attributes Returned from an Internal JSON HTTP Request to ViaCEP API with the DTO's CEP Attribute (Obtained through Path Variable 'id'). Returns an HTML Ok Status (200)")

	@PutMapping(value = "/{id}")
	public ResponseEntity<MedicalRegisterDTO> update(@PathVariable Long id,
			@Valid @RequestBody MedicalRegisterDTO dto) {

		dto = service.update(id, dto);

		return ResponseEntity.ok().body(dto);

	}

	@ApiOperation(value = "Performs a Soft Deletion Operation with a Preexistent MedicalRegister at the Database through the PathVariable Id, Updating the Object's 'deleted' Attribute Value to 'True' and Blocking its appearance on future Select/Search Queries. It does not Physically Delete it from the Database. Returns an HTTP No Content Response Status (204)")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> softDelete(@PathVariable Long id) {

		service.softDelete(id);

		return ResponseEntity.noContent().build();
	}

}
