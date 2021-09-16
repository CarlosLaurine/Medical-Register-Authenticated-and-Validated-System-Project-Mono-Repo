package com.carloslaurinedev.medicalregisterapi.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.carloslaurinedev.medicalregisterapi.dtos.RoleDTO;
import com.carloslaurinedev.medicalregisterapi.dtos.UserDTO;
import com.carloslaurinedev.medicalregisterapi.dtos.UserInsertDTO;
import com.carloslaurinedev.medicalregisterapi.dtos.UserUpdateDTO;
import com.carloslaurinedev.medicalregisterapi.entities.Role;
import com.carloslaurinedev.medicalregisterapi.entities.User;
import com.carloslaurinedev.medicalregisterapi.repositories.RoleRepository;
import com.carloslaurinedev.medicalregisterapi.repositories.UserRepository;
import com.carloslaurinedev.medicalregisterapi.services.exceptions.DBException;
import com.carloslaurinedev.medicalregisterapi.services.exceptions.ResourceNotFoundException;

@Service
public class UserService implements UserDetailsService {

	private static Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository repository;

	@Autowired
	private RoleRepository roleRepository;

	@org.springframework.transaction.annotation.Transactional(readOnly = true)
	public List<UserDTO> findAll() {

		List<User> entityList = repository.findAll();

		List<UserDTO> dtoList = entityList.stream().map(entity -> new UserDTO(entity)).collect(Collectors.toList());

		return dtoList;

	}

	@Transactional(readOnly = true)
	public Page<UserDTO> findAllPaged(Pageable pageable) {

		Page<User> entityPage = repository.findAll(pageable);

		Page<UserDTO> dtoPage = entityPage.map(entity -> new UserDTO(entity));

		return dtoPage;

	}

	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {

		Optional<User> obj = repository.findById(id);

		User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity Not Found"));

		UserDTO dto = new UserDTO(entity);

		return dto;

	}

	@Transactional
	public UserDTO insert(UserInsertDTO dto) {

		User entity = new User();

		tranformDtoIntoEntity(entity, dto);

		entity.setPassword(dto.getPassword());

		return new UserDTO(repository.save(entity));

	}

	@Transactional
	// In case the System runs on a different pom.xml Version than 2.4.4 where the
	// .getOne() Function is Deprecated and can be easily replaced by the getById()
	// Function
	@SuppressWarnings("deprecation")

	public UserDTO update(Long id, UserUpdateDTO dto) {

		try {

			User entity = repository.getOne(id);

			tranformDtoIntoEntity(entity, dto);

			entity = repository.save(entity);
			UserDTO userDTO = new UserDTO(entity);
			return userDTO;

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

	// In case the System runs on a different pom.xml Version than 2.4.4 where the
	// .getOne() Function is Deprecated and can be easily replaced by the getById()
	// Function
	@SuppressWarnings("deprecation")
	private void tranformDtoIntoEntity(User entity, UserDTO dto) {

		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setEmail(dto.getEmail());

		entity.getRoles().clear();

		for (RoleDTO roleDTO : dto.getRoles()) {

			Role role = roleRepository.getOne(roleDTO.getId());

			entity.getRoles().add(role);

		}

	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserDetails user = repository.findByEmail(username);
		if (user == null) {
			logger.error("User Not Found -> " + username);
			throw new UsernameNotFoundException("Email Not Found");

		}

		logger.info("User Found -> " + username);
		return user;
	}

}
