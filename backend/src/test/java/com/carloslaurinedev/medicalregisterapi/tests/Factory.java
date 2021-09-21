package com.carloslaurinedev.medicalregisterapi.tests;

import com.carloslaurinedev.medicalregisterapi.dtos.MedicalRegisterDTO;
import com.carloslaurinedev.medicalregisterapi.entities.MedicalRegister;
import com.carloslaurinedev.medicalregisterapi.entities.MedicalSpecialty;

public class Factory {

	public static MedicalRegister createDefaultMedicalRegister() {

		MedicalRegister register = new MedicalRegister(1L, "Carlos Laurine", 98643, 32239022L, 995702214L, 69911690);
		register.getSpecialties().add(new MedicalSpecialty(2L, "Angiologia"));
		register.getSpecialties().add(new MedicalSpecialty(3L, "Buco maxilo"));

		return register;
	}

	public static MedicalRegisterDTO createDefaultMedicalRegisterDTO() {

		MedicalRegister register = createDefaultMedicalRegister();

		MedicalRegisterDTO dto = new MedicalRegisterDTO(register, register.getSpecialties());

		return dto;

	}

	public static MedicalSpecialty createDefaultMedicalSpecialty() {

		return new MedicalSpecialty(2L, "Electronics");

	}
}
