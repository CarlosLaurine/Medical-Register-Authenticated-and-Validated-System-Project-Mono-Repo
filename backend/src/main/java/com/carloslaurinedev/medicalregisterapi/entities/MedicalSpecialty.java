package com.carloslaurinedev.medicalregisterapi.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "tb_specialty")

//Soft Delete Override Custom Update SQL Command to replace Entity Delete Commands reaching the Database
//while Updating Boolean Attribute "deleted" to "TRUE" instead of Physically Deleting the Data
@SQLDelete(sql = "UPDATE tb_specialty SET deleted = true WHERE id=?")
//SQL Conditional "where" filter added to future Select/Search Queries in order to make Soft Deleted Rows Invisible 
@Where(clause = "deleted=false")
public class MedicalSpecialty implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	private boolean deleted = Boolean.FALSE;

	@ManyToMany(mappedBy = "specialties")
	private Set<MedicalRegister> doctors = new HashSet<>();

	public MedicalSpecialty() {

	}

	public MedicalSpecialty(Long id, String name) {
		this.id = id;
		this.name = name;
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

	public boolean isDeleted() {
		return deleted;
	}

	public Set<MedicalRegister> getDoctors() {
		return doctors;
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
		MedicalSpecialty other = (MedicalSpecialty) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
