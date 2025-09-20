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
import com.library.controller.BookController;
import com.library.entity.Book;
import com.library.service.BookService;

@WebMvcTest(BookController.class)
public class BookControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookService libraryService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void registerBook_Success() throws Exception {
		Book book = new Book();
		book.setId(1L);
		book.setIsbn("123-4567890123");
		book.setTitle("Effective Java");
		book.setAuthor("Joshua Bloch");

		Mockito.when(libraryService.registerBook(any(Book.class))).thenReturn(book);

		mockMvc.perform(post("/api/books").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(book))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").value(1L)).andExpect(jsonPath("$.isbn").value("123-4567890123"))
				.andExpect(jsonPath("$.title").value("Effective Java"))
				.andExpect(jsonPath("$.author").value("Joshua Bloch"));
	}
}
