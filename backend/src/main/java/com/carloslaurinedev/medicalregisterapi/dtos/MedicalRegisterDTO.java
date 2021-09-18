package com.carloslaurinedev.medicalregisterapi.dtos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.carloslaurinedev.medicalregisterapi.entities.MedicalRegister;
import com.carloslaurinedev.medicalregisterapi.entities.MedicalSpecialty;

public class MedicalRegisterDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	@NotBlank
	@Size(max = 120, min = 3, message = "Name must contain between 3 and 120 Characters")
	private String name;

	@NotNull
	@Digits(integer = 7, fraction = 0, message = "CRM must contain a maximum of 7 Integer Number Characters")
	private Integer crm;

	@NotNull
	@Digits(integer = 13, fraction = 0, message = "Landline Phone must contain a maximum of 13 Integer Number Characters")
	private Long landlinePhone;

	@NotNull
	@Digits(integer = 13, fraction = 0, message = "CellPhone must contain a maximum of 13 Integer Number Characters")
	private Long cellPhone;

	@NotNull
	@Digits(integer = 9, fraction = 0, message = "CEP must contain a maximum of 9 Integer Number Characters")
	private Integer cep;

	@Size(min = 2, message = "Minimum of Specialities to be Included in the Register is TWO (2)")
	private List<MedicalSpecialtyDTO> specialties = new ArrayList<>();

	public MedicalRegisterDTO() {

	}

	public MedicalRegisterDTO(Long id, String name, Integer crm, Long landlinePhone, Long cellPhone, Integer cep) {
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

	public MedicalRegisterDTO(MedicalRegisterDTO dto) {
		id = dto.getId();
		name = dto.getName();
		crm = dto.getCrm();
		landlinePhone = dto.getLandlinePhone();
		cellPhone = dto.getCellPhone();
		cep = dto.getCep();
		dto.getSpecialties().forEach(specialty -> this.specialties.add(specialty));
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

	public Long getLandlinePhone() {
		return landlinePhone;
	}

	public void setLandlinePhone(Long landlinePhone) {
		this.landlinePhone = landlinePhone;
	}

	public Long getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(Long cellPhone) {
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
