package com.library.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.controller.BorrowerController;
import com.library.entity.Borrower;
import com.library.service.BorrowerService;

@WebMvcTest(BorrowerController.class)
public class BorrowerControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BorrowerService libraryService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void registerBorrower_Success() throws Exception {
		Borrower borrower = new Borrower();
		borrower.setId(1L);
		borrower.setName("John Doe");
		borrower.setEmail("john@example.com");

		Mockito.when(libraryService.registerBorrower(any(Borrower.class))).thenReturn(borrower);

		mockMvc.perform(post("/api/borrowers").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(borrower))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(1L)).andExpect(jsonPath("$.name").value("John Doe"))
				.andExpect(jsonPath("$.email").value("john@example.com"));
	}
}
