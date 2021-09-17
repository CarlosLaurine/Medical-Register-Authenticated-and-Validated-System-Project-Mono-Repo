package com.carloslaurinedev.medicalregisterapi.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.carloslaurinedev.medicalregisterapi.dtos.MedicalSpecialtyDTO;
import com.carloslaurinedev.medicalregisterapi.entities.MedicalSpecialty;
import com.carloslaurinedev.medicalregisterapi.repositories.MedicalSpecialtyRepository;
import com.carloslaurinedev.medicalregisterapi.services.exceptions.DBException;
import com.carloslaurinedev.medicalregisterapi.services.exceptions.ResourceNotFoundException;

@Service
public class MedicalSpecialtyService {

	@Autowired
	private MedicalSpecialtyRepository repository;

	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public List<MedicalSpecialtyDTO> findAll() {

		List<MedicalSpecialty> entityList = repository.findAll();

		List<MedicalSpecialtyDTO> dtoList = entityList.stream().map(entity -> new MedicalSpecialtyDTO(entity))
				.collect(Collectors.toList());

		return dtoList;

	}

	@Transactional(readOnly = true)
	public Page<MedicalSpecialtyDTO> findAllPaged(Pageable pageable) {

		Page<MedicalSpecialty> entityPage = repository.findAll(pageable);

		Page<MedicalSpecialtyDTO> dtoPage = entityPage.map(entity -> new MedicalSpecialtyDTO(entity));

		return dtoPage;

	}

	@Transactional(readOnly = true)
	public MedicalSpecialtyDTO findById(Long id) {

		Optional<MedicalSpecialty> obj = repository.findById(id);

		MedicalSpecialty entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found"));

		MedicalSpecialtyDTO dto = new MedicalSpecialtyDTO(entity);

		return dto;

	}

	@Transactional
	public MedicalSpecialtyDTO insert(MedicalSpecialtyDTO dto) {

		MedicalSpecialty entity = new MedicalSpecialty();

		entity.setName(dto.getName());

		dto = new MedicalSpecialtyDTO(repository.save(entity));

		return dto;
	}

	@Transactional
	// In case the System runs on a different pom.xml Version than 2.4.4 where the
	// .getOne() Function is Deprecated and can be easily replaced by the getById()
	// Function
	@SuppressWarnings("deprecation")

	public MedicalSpecialtyDTO update(Long id, MedicalSpecialtyDTO dto) {

		try {

			MedicalSpecialty entity = repository.getOne(id);
			entity.setName(dto.getName());
			entity = repository.save(entity);
			dto = new MedicalSpecialtyDTO(entity);
			return dto;

		} catch (EntityNotFoundException e) {

			throw new ResourceNotFoundException("Inexistent Id => " + id);

		}

	}

	public void delete(Long id) {

		try {

			repository.deleteById(id);

		}

		catch (EmptyResultDataAccessException e1) {

			throw new ResourceNotFoundException("Inexistent Id for Deletion => " + id);

		}

		catch (DataIntegrityViolationException e2) {

			throw new DBException("Data Integrity Violation");

		}
	}

}
