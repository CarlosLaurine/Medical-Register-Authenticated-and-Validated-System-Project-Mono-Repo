package com.carloslaurinedev.medicalregisterapi.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.carloslaurinedev.medicalregisterapi.dtos.MedicalRegisterDTO;
import com.carloslaurinedev.medicalregisterapi.entities.MedicalRegister;
import com.carloslaurinedev.medicalregisterapi.entities.MedicalSpecialty;
import com.carloslaurinedev.medicalregisterapi.repositories.MedicalRegisterRepository;
import com.carloslaurinedev.medicalregisterapi.repositories.MedicalSpecialtyRepository;
import com.carloslaurinedev.medicalregisterapi.services.exceptions.DBException;
import com.carloslaurinedev.medicalregisterapi.services.exceptions.ResourceNotFoundException;
import com.carloslaurinedev.medicalregisterapi.tests.Factory;

@ExtendWith(SpringExtension.class)
public class MedicalRegisterServiceUnitTests {

	@InjectMocks
	private MedicalRegisterService service;

	@Mock
	private MedicalRegisterRepository repository;

	@Mock
	private MedicalSpecialtyRepository specialtyRepository;

	private long existingId;
	private long nonExistingId;
	private long relatedId;
	private PageImpl<MedicalRegister> page;
	private MedicalRegister medicalRegister;
	private MedicalRegisterDTO dto;
	private MedicalSpecialty specialty;

	@BeforeEach
	void setUp() throws Exception {

		existingId = 1L;
		nonExistingId = 2000L;
		relatedId = 20L;
		medicalRegister = Factory.createDefaultMedicalRegister();
		page = new PageImpl<>(List.of(medicalRegister));
		specialty = Factory.createDefaultMedicalSpecialty();

		Mockito.doNothing().when(repository).deleteById(existingId);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExistingId);
		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(relatedId);

		Mockito.when(repository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

		Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(medicalRegister);

		Mockito.when(repository.findById(existingId)).thenReturn(Optional.of(medicalRegister));
		Mockito.when(repository.findById(nonExistingId)).thenReturn(Optional.empty());

		Mockito.when((repository).search(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
				.thenReturn(page);

		Mockito.when(repository.getOne(existingId)).thenReturn(medicalRegister);
		Mockito.doThrow(EntityNotFoundException.class).when(repository).getOne(nonExistingId);
		Mockito.when(specialtyRepository.getOne(ArgumentMatchers.anyLong())).thenReturn(specialty);
	}

	@Test
	public void softDeleteShouldDoNothingWhenIdExists() {

		Assertions.assertDoesNotThrow(() -> {
			service.softDelete(existingId);
		});

		Mockito.verify(repository, Mockito.times(1)).deleteById(existingId);
		;
	}

	@Test
	public void softDeleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.softDelete(nonExistingId);
		});

		Mockito.verify(repository, Mockito.times(1)).deleteById(nonExistingId);
		;
	}

	@Test
	public void softDeleteShouldThrowDBExceptionWhenIdIsRelatedToAnotherEntity() {

		Assertions.assertThrows(DBException.class, () -> {
			service.softDelete(relatedId);
		});

		Mockito.verify(repository, Mockito.times(1)).deleteById(relatedId);
		;
	}

	@Test
	public void selectAllPagedShouldReturnPage() {

		Pageable pageable = PageRequest.of(0, 12);

		Page<MedicalRegisterDTO> result = service.selectAllPaged(pageable, 0L, "");

		Assertions.assertNotNull(result);

		Mockito.verify(repository, Mockito.times(1)).search(pageable, null, "");

	}

	@Test
	public void searchByIdShouldReturnAMedicalRegisterDTOWhenIdExists() {

		dto = service.searchById(existingId);

		Assertions.assertNotNull(dto);

		Mockito.verify(repository, Mockito.times(1)).findById(existingId);

	}

	@Test
	public void searchByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {

			service.searchById(nonExistingId);

		});

		Mockito.verify(repository, Mockito.times(1)).findById(nonExistingId);

	}

	@Test
	public void updateShouldReturnAMedicalRegisterDTOWhenIdExists() {

		dto = service.update(existingId, Factory.createDefaultMedicalRegisterDTO());

		Assertions.assertNotNull(dto);

		Mockito.verify(specialtyRepository, Mockito.times(2)).getOne(ArgumentMatchers.anyLong());
		Mockito.verify(repository, Mockito.times(1)).getOne(existingId);

	}

	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {

		Assertions.assertThrows(ResourceNotFoundException.class, () -> {

			service.update(nonExistingId, Factory.createDefaultMedicalRegisterDTO());

		});

		Mockito.verify(repository, Mockito.times(1)).getOne(nonExistingId);

	}
}
