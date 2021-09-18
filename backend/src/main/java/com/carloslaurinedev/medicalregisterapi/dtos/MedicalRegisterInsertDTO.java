package com.carloslaurinedev.medicalregisterapi.dtos;

public class MedicalRegisterInsertDTO extends MedicalRegisterDTO {

	private static final long serialVersionUID = 1L;

	private ViaCepAddressDTO address;

	public MedicalRegisterInsertDTO() {

	}

	public MedicalRegisterInsertDTO(MedicalRegisterDTO registerDTO, ViaCepAddressDTO viaCepDTO) {
		super(registerDTO);
		address = viaCepDTO;

	}

	public ViaCepAddressDTO getAddress() {
		return address;
	}

	public void setAddress(ViaCepAddressDTO address) {
		this.address = address;
	}

}
