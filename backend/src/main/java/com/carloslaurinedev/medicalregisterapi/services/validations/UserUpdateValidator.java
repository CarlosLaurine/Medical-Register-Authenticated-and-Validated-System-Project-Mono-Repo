package com.carloslaurinedev.medicalregisterapi.services.validations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.carloslaurinedev.medicalregisterapi.dtos.UserUpdateDTO;
import com.carloslaurinedev.medicalregisterapi.entities.User;
import com.carloslaurinedev.medicalregisterapi.repositories.UserRepository;
import com.carloslaurinedev.medicalregisterapi.resources.exceptions.FieldMessage;

public class UserUpdateValidator implements ConstraintValidator<UserUpdateValid, UserUpdateDTO> {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private HttpServletRequest request;

	@Override
	public void initialize(UserUpdateValid ann) {
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean isValid(UserUpdateDTO dto, ConstraintValidatorContext context) {

		var uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		long userId = Long.parseLong(uriVars.get("id"));

		List<FieldMessage> list = new ArrayList<>();

		// Insert here the custom validation tests, then adding FieldMessage objects to
		// the list

		User user = userRepository.findByEmail(dto.getEmail());

		if (user != null && user.getId() != userId) {

			list.add(new FieldMessage("email", "Email already exists"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getField())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}