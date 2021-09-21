package com.carloslaurinedev.medicalregisterapi.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carloslaurinedev.medicalregisterapi.entities.MedicalRegister;
import com.carloslaurinedev.medicalregisterapi.entities.MedicalSpecialty;
import com.carloslaurinedev.medicalregisterapi.entities.Role;
import com.carloslaurinedev.medicalregisterapi.entities.User;
import com.carloslaurinedev.medicalregisterapi.repositories.MedicalRegisterRepository;
import com.carloslaurinedev.medicalregisterapi.repositories.MedicalSpecialtyRepository;
import com.carloslaurinedev.medicalregisterapi.repositories.RoleRepository;
import com.carloslaurinedev.medicalregisterapi.repositories.UserRepository;

@Service
public class DataBaseService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	MedicalRegisterRepository medicalRegisterRepository;

	@Autowired
	MedicalSpecialtyRepository medicalSpecialtyRepository;

	public void instantiateTestDatabase() {

		Role roleOperator = new Role(null, "ROLE_OPERATOR");
		Role roleAdmin = new Role(null, "ROLE_ADMIN");

		roleRepository.save(roleOperator);
		roleRepository.save(roleAdmin);

		Set<Role> rolesOperator = new HashSet<>();
		Set<Role> rolesOperatorAndAdmin = new HashSet<>();

		rolesOperator.add(roleOperator);
		rolesOperatorAndAdmin.add(roleOperator);
		rolesOperatorAndAdmin.add(roleAdmin);

		User userOperator = new User(null, "Carlos", "Pinho", "carlos@gmail.com",
				"$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG", rolesOperator);

		User userOperatorAndAdmin = new User(null, "Níkolas", "Lencioni", "nikolas@gmail.com",
				"$2a$10$eACCYoNOHEqXve8aIWT8Nu3PkMXWBaOxJ9aORUYzfMQCbVBIhZ8tG", rolesOperatorAndAdmin);

		userRepository.save(userOperator);
		userRepository.save(userOperatorAndAdmin);

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

		MedicalRegister medicalRegister1 = new MedicalRegister(null, "José de Assis", 74259, 36247170L, 991345699L,
				49038561, medicalSpecialtySet1);
		MedicalRegister medicalRegister2 = new MedicalRegister(null, "Carlos Laurine", 98643, 32239022L, 995702214L,
				69911690, medicalSpecialtySet2);
		MedicalRegister medicalRegister3 = new MedicalRegister(null, "Plínio Borges", 81885, 36259997L, 987252233L,
				81510490, medicalSpecialtySet3);
		MedicalRegister medicalRegister4 = new MedicalRegister(null, "Renan Marinho", 32190, 32243638L, 988839516L,
				68905540, medicalSpecialtySet4);

		medicalRegisterRepository.save(medicalRegister1);
		medicalRegisterRepository.save(medicalRegister2);
		medicalRegisterRepository.save(medicalRegister3);
		medicalRegisterRepository.save(medicalRegister4);

	}

}
