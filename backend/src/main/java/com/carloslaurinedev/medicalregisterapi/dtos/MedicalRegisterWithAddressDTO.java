package com.carloslaurinedev.medicalregisterapi.dtos;

public class MedicalRegisterWithAddressDTO extends MedicalRegisterDTO {

	private static final long serialVersionUID = 1L;

	private ViaCepAddressDTO address;

	public MedicalRegisterWithAddressDTO() {

	}

	public MedicalRegisterWithAddressDTO(MedicalRegisterDTO registerDTO, ViaCepAddressDTO viaCepDTO) {
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
