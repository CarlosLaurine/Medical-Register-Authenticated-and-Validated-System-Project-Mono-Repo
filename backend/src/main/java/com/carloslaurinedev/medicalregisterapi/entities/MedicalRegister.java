package com.carloslaurinedev.medicalregisterapi.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "tb_register", uniqueConstraints = @UniqueConstraint(columnNames = { "crm", "cellPhone" }))

//Soft Delete Override Custom Update SQL Command to replace Entity Delete Commands reaching the Database
//while Updating Boolean Attribute "deleted" to "TRUE" instead of Physically Deleting the Data
@SQLDelete(sql = "UPDATE tb_register SET deleted = true WHERE id=?")
//SQL Conditional "where" filter added to future Select/Search Queries in order to make Soft Deleted Rows Invisible 
@Where(clause = "deleted=false")

public class MedicalRegister implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	private Integer crm;
	private Long landlinePhone;
	private Long cellPhone;
	private Integer cep;

	private boolean deleted = Boolean.FALSE;

	@ManyToMany
	@JoinTable(name = "tb_register_specialty", joinColumns = @JoinColumn(name = "register_id"), inverseJoinColumns = @JoinColumn(name = "specialty_id"))
	private Set<MedicalSpecialty> specialties = new HashSet<>();

	public MedicalRegister() {

	}

	public MedicalRegister(Long id, String name, Integer crm, Long landlinePhone, Long cellPhone, Integer cep) {
		super();
		this.id = id;
		this.name = name;
		this.crm = crm;
		this.landlinePhone = landlinePhone;
		this.cellPhone = cellPhone;
		this.cep = cep;
	}

	public MedicalRegister(Long id, String name, Integer crm, Long landlinePhone, Long cellPhone, Integer cep,
			Set<MedicalSpecialty> specialties) {
		this();
		this.specialties = specialties;
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

	public boolean isDeleted() {
		return deleted;
	}

	public Set<MedicalSpecialty> getSpecialties() {
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
		MedicalRegister other = (MedicalRegister) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
