package com.carloslaurinedev.medicalregisterapi.services;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.carloslaurinedev.medicalregisterapi.dtos.MedicalRegisterDTO;
import com.carloslaurinedev.medicalregisterapi.repositories.MedicalRegisterRepository;
import com.carloslaurinedev.medicalregisterapi.services.exceptions.ResourceNotFoundException;

@SpringBootTest
@Transactional
public class MedicalRegisterServiceIntegrationTests {

	@Autowired
	private MedicalRegisterService service;

	@Autowired
	private MedicalRegisterRepository repository;

	private long existingId;
	private long nonExistingId;
	private long countTotalMedicalRegisters;

	@BeforeEach
	void setUp() throws Exception {
		existingId = 2L;
		nonExistingId = 25L;
		countTotalMedicalRegisters = 4L;
	}

	@Test
	public void softDeleteShouldDeleteResourceWhenIdExists() {

		service.softDelete(existingId);

		Assertions.assertEquals(countTotalMedicalRegisters - 1, repository.count());
	}

	@Test
	public void softDeleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {

			service.softDelete(nonExistingId);

		});
	}

	@Test
	public void selectAllPagedShouldReturnPageWhenPageHasNumber0AndSize10() {

		PageRequest pageable = PageRequest.of(0, 10);

		Page<MedicalRegisterDTO> page = service.selectAllPaged(pageable, 0L, "");

		Assertions.assertTrue(page.hasContent());

		Assertions.assertEquals(0, page.getNumber());
		Assertions.assertEquals(10, page.getSize());
		Assertions.assertEquals(countTotalMedicalRegisters, page.getTotalElements());

	}

	@Test
	public void selectAllPagedShouldReturnSortedPageWhenSortByNameIsRequested() {

		PageRequest pageable = PageRequest.of(0, 10, Sort.by("name"));

		Page<MedicalRegisterDTO> page = service.selectAllPaged(pageable, 0L, "");

		Assertions.assertFalse(page.isEmpty());

		Assertions.assertEquals("Carlos Laurine", page.getContent().get(0).getName());
		Assertions.assertEquals("José de Assis", page.getContent().get(1).getName());
		Assertions.assertEquals("Plínio Borges", page.getContent().get(2).getName());

	}

	@Test
	public void selectAllPagedShouldReturnEmptyPageWhenPageDoesNotExist() {

		PageRequest pageable = PageRequest.of(4, 10);

		Page<MedicalRegisterDTO> page = service.selectAllPaged(pageable, 0L, "");

		Assertions.assertTrue(page.isEmpty());

	}

}
