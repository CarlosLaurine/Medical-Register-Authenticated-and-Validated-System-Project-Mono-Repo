package com.carloslaurinedev.medicalregisterapi.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.carloslaurinedev.medicalregisterapi.dtos.MedicalRegisterDTO;
import com.carloslaurinedev.medicalregisterapi.tests.Factory;
import com.carloslaurinedev.medicalregisterapi.tests.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MedicalRegisterControllerIntegrationTests {

	@Autowired
	private MockMvc mockMVC;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private TokenUtil tokenUtil;

	private long existingId;
	private long nonExistingId;
	private long countTotalMedicalRegisters;
	private MedicalRegisterDTO dto;

	private String username;
	private String password;

	@BeforeEach
	void setUp() throws Exception {
		existingId = 2L;
		nonExistingId = 25L;
		countTotalMedicalRegisters = 4L;
		dto = Factory.createDefaultMedicalRegisterDTO();

		username = "nikolas@gmail.com";
		password = "123456";
	}

	@Test
	public void findAllPagedShouldReturnSortedPageWhenSortByNameInDescendentOrderIsRequested() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMVC, username, password);

		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMVC.perform(get("/gcb-medical-register-api/doctors?page=0&size=12&sort=name,desc")
				.header("Authorization", "Bearer " + accessToken).content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());

		result.andExpect(jsonPath("$.content").exists());
		result.andExpect(jsonPath("$.totalElements").value(countTotalMedicalRegisters));
		result.andExpect(jsonPath("$.size").value("12"));
		result.andExpect(jsonPath("$.number").value("0"));
		result.andExpect(jsonPath("$.sort.sorted").value("true"));

		result.andExpect(jsonPath("$.content[0].name").value("Renan Marinho"));
		result.andExpect(jsonPath("$.content[1].name").value("Plínio Borges"));
		result.andExpect(jsonPath("$.content[2].name").value("José de Assis"));

	}

	@Test
	public void updateShouldReturnMedicalRegisterDTOAndOkStatusWhenIdExists() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMVC, username, password);

		String jsonBody = objectMapper.writeValueAsString(dto);

		String expectedName = dto.getName();
		Long expectedCellphone = dto.getCellPhone();
		Long expectedLandlinePhone = dto.getLandlinePhone();
		Integer expectedCep = dto.getCep();
		Integer expectedCrm = dto.getCrm();


		ResultActions result = mockMVC.perform(put("/gcb-medical-register-api/doctors/{id}", existingId)
				.header("Authorization", "Bearer " + accessToken).content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").value(existingId));
		result.andExpect(jsonPath("$.name").value(expectedName));
		result.andExpect(jsonPath("$.cellPhone").value(expectedCellphone));
		result.andExpect(jsonPath("$.landlinePhone").value(expectedLandlinePhone));
		result.andExpect(jsonPath("$.cep").value(expectedCep));
		result.andExpect(jsonPath("$.crm").value(expectedCrm));

	}

	@Test
	public void updateShouldReturnNotFoundStatusWhenIdDoesNotExist() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMVC, username, password);

		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMVC.perform(put("/gcb-medical-register-api/doctors/{id}", nonExistingId)
				.header("Authorization", "Bearer " + accessToken).content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNotFound());
	}

}
