package com.carloslaurinedev.medicalregisterapi.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.carloslaurinedev.medicalregisterapi.entities.MedicalRegister;
import com.carloslaurinedev.medicalregisterapi.entities.MedicalSpecialty;

@Repository
public interface MedicalRegisterRepository extends JpaRepository<MedicalRegister, Long> {

	@Query("SELECT DISTINCT doctor FROM MedicalRegister doctor INNER JOIN doctor.specialties specs WHERE "
			+ "(COALESCE(:specialties) IS NULL OR specs in :specialties) " + "AND "
			+ "(:name = '' OR LOWER(doctor.name) LIKE LOWER (CONCAT('%',:name,'%')))")
	Page<MedicalRegister> search(Pageable pageable, List<MedicalSpecialty> specialties, String name);

	@Query("SELECT doctor from MedicalRegister doctor JOIN FETCH doctor.specialties WHERE doctor IN :doctors")
	List<MedicalRegister> findDoctorsWithTheirRespectiveSpecialties(List<MedicalRegister> doctors);

	MedicalRegister findByName(String name);

	MedicalRegister findByCrm(Integer crm);

	MedicalRegister findBylandlinePhone(Integer cell);

	MedicalRegister findBycellPhone(Integer landlinePhone);

}
