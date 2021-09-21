package com.carloslaurinedev.medicalregisterapi.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.carloslaurinedev.medicalregisterapi.dtos.MedicalRegisterDTO;
import com.carloslaurinedev.medicalregisterapi.services.MedicalRegisterService;
import com.carloslaurinedev.medicalregisterapi.services.exceptions.DBException;
import com.carloslaurinedev.medicalregisterapi.services.exceptions.ResourceNotFoundException;
import com.carloslaurinedev.medicalregisterapi.tests.Factory;
import com.carloslaurinedev.medicalregisterapi.tests.TokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRegisterControllerUnitTests {

	@Autowired
	private MockMvc mockMVC;

	@MockBean
	private MedicalRegisterService service;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private TokenUtil tokenUtil;

	private MedicalRegisterDTO medicalRegisterDTO;
	private PageImpl<MedicalRegisterDTO> page;

	private long existingId;
	private long nonExistingId;
	private long relatedId;

	private String username;
	private String password;

	@BeforeEach
	public void setUp() throws Exception {

		medicalRegisterDTO = Factory.createDefaultMedicalRegisterDTO();
		page = new PageImpl<>(List.of(medicalRegisterDTO));
		existingId = 2L;
		nonExistingId = 25L;
		relatedId = 3L;

		username = "nikolas@gmail.com";
		password = "123456";

		when(service.selectAllPaged(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
				.thenReturn(page);

		when(service.searchById(existingId)).thenReturn(medicalRegisterDTO);
		doThrow(ResourceNotFoundException.class).when(service).searchById(nonExistingId);

		when(service.update(eq(existingId), any())).thenReturn(medicalRegisterDTO);
		doThrow(ResourceNotFoundException.class).when(service).update(eq(nonExistingId),
				ArgumentMatchers.any(MedicalRegisterDTO.class));

		doNothing().when(service).softDelete(existingId);
		doThrow(ResourceNotFoundException.class).when(service).softDelete(nonExistingId);
		doThrow(DBException.class).when(service).softDelete(relatedId);

		when(service.insert(any())).thenReturn(medicalRegisterDTO);
	}

	@Test
	public void selectAllShouldReturnPage() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMVC, username, password);

		String jsonBody = objectMapper.writeValueAsString(medicalRegisterDTO);

		ResultActions result = mockMVC
				.perform(get("/gcb-medical-register-api/doctors").header("Authorization", "Bearer " + accessToken)
						.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
	}

	@Test
	public void searchByIdShouldReturnMedicalRegisterDTOAndOkStatusWhenIdExists() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMVC, username, password);

		String jsonBody = objectMapper.writeValueAsString(medicalRegisterDTO);

		ResultActions result = mockMVC.perform(get("/gcb-medical-register-api/doctors/{id}", existingId)
				.header("Authorization", "Bearer " + accessToken)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());

		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.name").exists());
		result.andExpect(jsonPath("$.cellPhone").exists());
		result.andExpect(jsonPath("$.landlinePhone").exists());
		result.andExpect(jsonPath("$.crm").exists());
		result.andExpect(jsonPath("$.cep").exists());

	}

	@Test
	public void searchByIdShouldReturnNotFoundStatusWhenIdDoesNotExist() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMVC, username, password);

		String jsonBody = objectMapper.writeValueAsString(medicalRegisterDTO);

		ResultActions result = mockMVC.perform(get("/gcb-medical-register-api/doctors/{id}", nonExistingId)
				.header("Authorization", "Bearer " + accessToken).content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNotFound());

	}

	@Test
	public void updateShouldReturnMedicalRegisterDTOAndOkStatusWhenIdExists() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMVC, username, password);

		String jsonBody = objectMapper.writeValueAsString(medicalRegisterDTO);

		ResultActions result = mockMVC.perform(put("/gcb-medical-register-api/doctors/{id}", existingId)
				.header("Authorization", "Bearer " + accessToken).content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());

		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.name").exists());
		result.andExpect(jsonPath("$.cellPhone").exists());
		result.andExpect(jsonPath("$.landlinePhone").exists());
		result.andExpect(jsonPath("$.crm").exists());
		result.andExpect(jsonPath("$.cep").exists());

	}

	@Test
	public void updateShouldReturnNotFoundStatusWhenIdDoesNotExist() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMVC, username, password);

		String jsonBody = objectMapper.writeValueAsString(medicalRegisterDTO);

		ResultActions result = mockMVC.perform(put("/gcb-medical-register-api/doctors/{id}", nonExistingId)
				.header("Authorization", "Bearer " + accessToken).content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isNotFound());

	}

	@Test
	public void softDeleteShouldReturnNoContentStatusWhenIdExists() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMVC, username, password);

		ResultActions actions = mockMVC.perform(delete("/gcb-medical-register-api/doctors/{id}", existingId)
				.header("Authorization", "Bearer " + accessToken));

		actions.andExpect(status().isNoContent());
	}

	@Test
	public void softDeleteShouldReturnNotFoundStatusWhenIdDoesNotExist() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMVC, username, password);

		ResultActions actions = mockMVC.perform(delete("/gcb-medical-register-api/doctors/{id}", nonExistingId)
				.header("Authorization", "Bearer " + accessToken));

		actions.andExpect(status().isNotFound());
	}

	@Test
	public void insertShouldReturnMedicalRegisterDTOAndCreatedStatus() throws Exception {

		String accessToken = tokenUtil.obtainAccessToken(mockMVC, username, password);

		String jsonBody = objectMapper.writeValueAsString(medicalRegisterDTO);

		ResultActions result = mockMVC
				.perform(post("/gcb-medical-register-api/doctors").header("Authorization", "Bearer " + accessToken)
						.content(jsonBody).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isCreated());

		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.name").exists());
		result.andExpect(jsonPath("$.cellPhone").exists());
		result.andExpect(jsonPath("$.landlinePhone").exists());
		result.andExpect(jsonPath("$.crm").exists());
		result.andExpect(jsonPath("$.cep").exists());
	}
}
