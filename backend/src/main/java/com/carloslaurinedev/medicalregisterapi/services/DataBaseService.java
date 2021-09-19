package com.carloslaurinedev.medicalregisterapi.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carloslaurinedev.medicalregisterapi.entities.MedicalRegister;
import com.carloslaurinedev.medicalregisterapi.entities.MedicalSpecialty;
import com.carloslaurinedev.medicalregisterapi.repositories.MedicalRegisterRepository;
import com.carloslaurinedev.medicalregisterapi.repositories.MedicalSpecialtyRepository;

@Service
public class DataBaseService {

	@Autowired
	MedicalRegisterRepository medicalRegisterRepository;
	
	@Autowired
	MedicalSpecialtyRepository medicalSpecialtyRepository;

	public void instantiateTestDatabase() {

		MedicalSpecialty medicalSpecialty1 = new MedicalSpecialty(null, "Alergologia");
		MedicalSpecialty medicalSpecialty2 = new MedicalSpecialty(null, "Angiologia");
		MedicalSpecialty medicalSpecialty3 = new MedicalSpecialty(null, "Buco maxilo");
		MedicalSpecialty medicalSpecialty4 = new MedicalSpecialty(null, "Cardiologia clínica");
		MedicalSpecialty medicalSpecialty5 = new MedicalSpecialty(null, "Cardiologia infantil");
		MedicalSpecialty medicalSpecialty6 = new MedicalSpecialty(null, "Cirurgia cabeça e pescoço");
		MedicalSpecialty medicalSpecialty7 = new MedicalSpecialty(null, "Cirurgia cardiaca");
		MedicalSpecialty medicalSpecialty8 = new MedicalSpecialty(null, "Cirurgia de toráx");
	
		medicalSpecialtyRepository.save(medicalSpecialty1);
		medicalSpecialtyRepository.save(medicalSpecialty2);
		medicalSpecialtyRepository.save(medicalSpecialty3);
		medicalSpecialtyRepository.save(medicalSpecialty4);
		medicalSpecialtyRepository.save(medicalSpecialty5);
		medicalSpecialtyRepository.save(medicalSpecialty6);
		medicalSpecialtyRepository.save(medicalSpecialty7);
		medicalSpecialtyRepository.save(medicalSpecialty8);

		Set<MedicalSpecialty> medicalSpecialtySet1 = new HashSet<>();
		Set<MedicalSpecialty> medicalSpecialtySet2 = new HashSet<>();
		Set<MedicalSpecialty> medicalSpecialtySet3 = new HashSet<>();
		Set<MedicalSpecialty> medicalSpecialtySet4 = new HashSet<>();

		medicalSpecialtySet1.add(medicalSpecialty1);
		medicalSpecialtySet2.add(medicalSpecialty2);
		medicalSpecialtySet1.add(medicalSpecialty3);
		medicalSpecialtySet2.add(medicalSpecialty4);
		medicalSpecialtySet3.add(medicalSpecialty5);
		medicalSpecialtySet4.add(medicalSpecialty6);
		medicalSpecialtySet3.add(medicalSpecialty7);
		medicalSpecialtySet4.add(medicalSpecialty8);

		MedicalRegister medicalRegister1 = new MedicalRegister(null,"José de Assis", 74259, 36247170L, 991345699L, 49038561, medicalSpecialtySet1);
		MedicalRegister medicalRegister2 = new MedicalRegister(null,"Carlos Laurine", 98643, 32239022L, 995702214L, 69911690, medicalSpecialtySet2);
		MedicalRegister medicalRegister3 = new MedicalRegister(null,"Plínio Borges", 81885, 36259997L, 987252233L, 81510490, medicalSpecialtySet3);
		MedicalRegister medicalRegister4 = new MedicalRegister(null,"Renan Marinho", 32190, 32243638L, 988839516L, 68905540, medicalSpecialtySet4);
		
		medicalRegisterRepository.save(medicalRegister1);
		medicalRegisterRepository.save(medicalRegister2);
		medicalRegisterRepository.save(medicalRegister3);
		medicalRegisterRepository.save(medicalRegister4);
		
	}

}
