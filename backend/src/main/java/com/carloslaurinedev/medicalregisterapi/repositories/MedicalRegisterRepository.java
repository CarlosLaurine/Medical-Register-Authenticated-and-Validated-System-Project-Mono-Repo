package com.carloslaurinedev.medicalregisterapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.carloslaurinedev.medicalregisterapi.entities.MedicalRegister;

@Repository
public interface MedicalRegisterRepository extends JpaRepository<MedicalRegister, Long> {

	MedicalRegister findByName(String name);

	MedicalRegister findByCrm(Integer crm);

	MedicalRegister findBylandlinePhone(Integer cell);

	MedicalRegister findBycellPhone(Integer landlinePhone);

}
