package com.carloslaurinedev.medicalregisterapi.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.carloslaurinedev.medicalregisterapi.dtos.MedicalRegisterDTO;
import com.carloslaurinedev.medicalregisterapi.dtos.MedicalSpecialtyDTO;
import com.carloslaurinedev.medicalregisterapi.entities.MedicalRegister;
import com.carloslaurinedev.medicalregisterapi.entities.MedicalSpecialty;
import com.carloslaurinedev.medicalregisterapi.repositories.MedicalRegisterRepository;
import com.carloslaurinedev.medicalregisterapi.repositories.MedicalSpecialtyRepository;
import com.carloslaurinedev.medicalregisterapi.services.exceptions.DBException;
import com.carloslaurinedev.medicalregisterapi.services.exceptions.ResourceNotFoundException;

@Service
public class MedicalRegisterService {

	@Autowired
	private MedicalRegisterRepository repository;

	@Autowired
	private MedicalSpecialtyRepository medicalSpecialtyRepository;

	@Transactional(readOnly = true)
	public Page<MedicalRegisterDTO> selectAllPaged(Pageable pageable, Long categoryId, String name) {

		List<MedicalSpecialty> specialties = (categoryId == 0) ? null
				: Arrays.asList(medicalSpecialtyRepository.getOne(categoryId));

		Page<MedicalRegister> entityPage = repository.search(pageable, specialties, name);

		repository.findDoctorsWithTheirRespectiveSpecialties(entityPage.getContent());

		Page<MedicalRegisterDTO> dtoPage = entityPage
				.map(entity -> new MedicalRegisterDTO(entity, entity.getSpecialties()));

		return dtoPage;

	}

	@Transactional(readOnly = true)
	public MedicalRegisterDTO searchById(Long id) {

		Optional<MedicalRegister> obj = repository.findById(id);

		MedicalRegister entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found"));

		MedicalRegisterDTO dto = new MedicalRegisterDTO(entity, entity.getSpecialties());

		return dto;

	}

	@Transactional(readOnly = true)
	public List<MedicalRegisterDTO> searchByName(String name) {

		try {

			List<MedicalRegister> entityList = repository.findByName(name);

			return tranformEntityListIntoDtoList(entityList);

		} catch (IllegalArgumentException e) {

			throw new ResourceNotFoundException("Entity Not Found");

		}

	}

	@Transactional(readOnly = true)
	public MedicalRegisterDTO searchByCrm(Integer crm) {

		MedicalRegister entity = repository.findByCrm(crm);

		if (entity == null) {
			throw new ResourceNotFoundException("Entity Not Found");
		}

		MedicalRegisterDTO dto = new MedicalRegisterDTO(entity, entity.getSpecialties());

		return dto;

	}

	@Transactional(readOnly = true)
	public List<MedicalRegisterDTO> searchByLandlinePhone(Long landlinePhone) {

		try {

			List<MedicalRegister> entityList = repository.findByLandlinePhone(landlinePhone);

			return tranformEntityListIntoDtoList(entityList);

		} catch (IllegalArgumentException e) {

			throw new ResourceNotFoundException("Entity Not Found");

		}

	}

	@Transactional(readOnly = true)
	public MedicalRegisterDTO searchByCellphone(Long cellPhone) {

		MedicalRegister entity = repository.findByCellPhone(cellPhone);

		if (entity == null) {
			throw new ResourceNotFoundException("Entity Not Found");
		}

		MedicalRegisterDTO dto = new MedicalRegisterDTO(entity, entity.getSpecialties());

		return dto;

	}

	@Transactional(readOnly = true)
	public List<MedicalRegisterDTO> searchByCep(Integer cep) {

		try {

			List<MedicalRegister> entityList = repository.findByCep(cep);

			return tranformEntityListIntoDtoList(entityList);

		} catch (IllegalArgumentException e) {

			throw new ResourceNotFoundException("Entity Not Found");

		}

	}

	@Transactional
	public MedicalRegisterDTO insert(MedicalRegisterDTO dto) {

		MedicalRegister entity = new MedicalRegister();

		tranformDtoIntoEntity(entity, dto);

		dto = new MedicalRegisterDTO(repository.save(entity), entity.getSpecialties());

		return dto;
	}

	@Transactional
	public MedicalRegisterDTO update(Long id, MedicalRegisterDTO dto) {

		try {

			MedicalRegister entity = repository.getOne(id);

			tranformDtoIntoEntity(entity, dto);

			entity = repository.save(entity);
			dto = new MedicalRegisterDTO(entity);
			return dto;

		} catch (EntityNotFoundException e) {

			throw new ResourceNotFoundException("Inexistent Id => " + id);

		}

	}

	public void softDelete(Long id) {

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

	private void tranformDtoIntoEntity(MedicalRegister entity, MedicalRegisterDTO dto) {

		entity.setName(dto.getName());
		entity.setCrm(dto.getCrm());
		entity.setLandlinePhone(dto.getLandlinePhone());
		entity.setCellPhone(dto.getCellPhone());
		entity.setCep(dto.getCep());

		// Clearing all possible pre-existing specialties at the entity
		entity.getSpecialties().clear();

		for (MedicalSpecialtyDTO specialtyDto : dto.getSpecialties()) {

			MedicalSpecialty specialtyEntity = medicalSpecialtyRepository.getOne(specialtyDto.getId());

			entity.getSpecialties().add(specialtyEntity);

		}
	}

	private List<MedicalRegisterDTO> tranformEntityListIntoDtoList(List<MedicalRegister> entityList) {

		List<MedicalRegisterDTO> dtoList = new ArrayList<>();

		for (MedicalRegister medicalRegister : entityList) {

			dtoList.add(new MedicalRegisterDTO(medicalRegister, medicalRegister.getSpecialties()));

		}

		return dtoList;

	}
}
