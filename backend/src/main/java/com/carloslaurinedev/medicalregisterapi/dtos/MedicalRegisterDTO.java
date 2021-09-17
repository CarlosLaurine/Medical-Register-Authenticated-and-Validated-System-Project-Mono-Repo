package com.carloslaurinedev.medicalregisterapi.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.carloslaurinedev.medicalregisterapi.entities.MedicalRegister;
import com.carloslaurinedev.medicalregisterapi.entities.MedicalSpecialty;

public class MedicalRegisterDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private Integer crm;
	private Integer landlinePhone;
	private Integer cellPhone;
	private Integer cep;

	private List<MedicalSpecialtyDTO> specialties = new ArrayList<>();

	public MedicalRegisterDTO() {

	}

	public MedicalRegisterDTO(Long id, String name, Integer crm, Integer landlinePhone, Integer cellPhone,
			Integer cep) {
		this.id = id;
		this.name = name;
		this.crm = crm;
		this.landlinePhone = landlinePhone;
		this.cellPhone = cellPhone;
		this.cep = cep;
	}

	public MedicalRegisterDTO(MedicalRegister entity) {
		id = entity.getId();
		name = entity.getName();
		crm = entity.getCrm();
		landlinePhone = entity.getLandlinePhone();
		cellPhone = entity.getCellPhone();
		cep = entity.getCep();
	}

	public MedicalRegisterDTO(MedicalRegister doctor, Set<MedicalSpecialty> specialties) {

		this(doctor);

		specialties.forEach(specialty -> this.specialties.add(new MedicalSpecialtyDTO(specialty)));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCrm() {
		return crm;
	}

	public void setCrm(Integer crm) {
		this.crm = crm;
	}

	public Integer getLandlinePhone() {
		return landlinePhone;
	}

	public void setLandlinePhone(Integer landlinePhone) {
		this.landlinePhone = landlinePhone;
	}

	public Integer getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(Integer cellPhone) {
		this.cellPhone = cellPhone;
	}

	public Integer getCep() {
		return cep;
	}

	public void setCep(Integer cep) {
		this.cep = cep;
	}

	public List<MedicalSpecialtyDTO> getSpecialties() {
		return specialties;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MedicalRegisterDTO other = (MedicalRegisterDTO) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
