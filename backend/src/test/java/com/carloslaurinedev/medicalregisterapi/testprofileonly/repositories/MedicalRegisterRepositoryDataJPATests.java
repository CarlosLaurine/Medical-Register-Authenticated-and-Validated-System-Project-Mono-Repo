package com.carloslaurinedev.medicalregisterapi.testprofileonly.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.carloslaurinedev.medicalregisterapi.entities.MedicalRegister;
import com.carloslaurinedev.medicalregisterapi.repositories.MedicalRegisterRepository;
import com.carloslaurinedev.medicalregisterapi.tests.Factory;

//OBS: To Run those tests properly H2 Test Database must EXIST, so spring.profiles.active property at application.properties File must be set as "test"

@DataJpaTest
public class MedicalRegisterRepositoryDataJPATests {

	@Autowired
	MedicalRegisterRepository repository;

	private long existingId;
	private long nonExistingId;
	private long countTotalMedicalRegisters;

	@BeforeEach
	void setup() throws Exception {

		existingId = 2L;
		nonExistingId = 25L;
		countTotalMedicalRegisters = 4L; // Number of pre-existent Insertions at the Test DB H2
	}

	@Test
	public void saveShouldPersistWithAutoIncrementationWhenIdIsNull() {

		MedicalRegister medicalRegister = Factory.createDefaultMedicalRegister();

		medicalRegister.setId(null);
		medicalRegister.setName("Carlos Pinho");
		medicalRegister.setLandlinePhone(36231513L);
		medicalRegister.setCellPhone(71992945678L);
		medicalRegister.setCrm(99241);
		medicalRegister.setCep(44110070);
		medicalRegister.setDeleted(false);

		medicalRegister = repository.save(medicalRegister);

		Assertions.assertNotNull(medicalRegister.getId());
		Assertions.assertEquals(countTotalMedicalRegisters + 1, medicalRegister.getId());

	}

	@Test
	public void findByIdShouldReturnANotNullOptionalWhenIdExists() {

		Long existentId = countTotalMedicalRegisters;

		Optional<MedicalRegister> optional = repository.findById(existentId);

		Assertions.assertTrue(optional.isPresent());
	}

	@Test
	public void findByIdShouldReturnANullOptionalWhenIdDoesNotExist() {

		Long nonExistentId = countTotalMedicalRegisters + 200;

		Optional<MedicalRegister> optional = repository.findById(nonExistentId);

		Assertions.assertFalse(optional.isPresent());

	}

	@Test
	public void deleteShouldDeleteObjectWhenExistentIdIsInformed() {

		repository.deleteById(existingId);

		Optional<MedicalRegister> medicalRegister = repository.findById(existingId);

		Assertions.assertFalse(medicalRegister.isPresent());

	}

	@Test
	public void deleteShouldThrowEmptyResultDataAccessExceptionWhenNonexistentIdIsInformed() {

		Assertions.assertThrows(EmptyResultDataAccessException.class, () -> {

			repository.deleteById(nonExistingId);

		});

	}
}
