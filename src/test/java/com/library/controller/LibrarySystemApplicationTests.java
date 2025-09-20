package com.library.controller;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.entity.Book;
import com.library.entity.Borrower;
import com.library.repository.BookRepository;
import com.library.repository.BorrowedBookRepository;
import com.library.repository.BorrowerRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class LibrarySystemApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private BorrowerRepository borrowerRepository;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private BorrowedBookRepository borrowedBookRepository;

	private Borrower testBorrower;
	private Book testBook;

	@BeforeEach
	void setup() throws Exception {
		// Delete borrowed_book entries first to avoid FK constraint errors
		borrowedBookRepository.deleteAll();
		// Then delete borrowers and books
		borrowerRepository.deleteAll();
		bookRepository.deleteAll();

		// Register a borrower
		Borrower borrower = new Borrower();
		borrower.setName("Test User");
		borrower.setEmail("testuser@example.com");

		String borrowerJson = objectMapper.writeValueAsString(borrower);
		String borrowerResponse = mockMvc
				.perform(post("/api/borrowers").contentType(MediaType.APPLICATION_JSON).content(borrowerJson))
				.andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
		testBorrower = objectMapper.readValue(borrowerResponse, Borrower.class);

		// Register a book
		Book book = new Book();
		book.setIsbn("9876543210");
		book.setTitle("Test Driven Development");
		book.setAuthor("Kent Beck");

		String bookJson = objectMapper.writeValueAsString(book);
		String bookResponse = mockMvc
				.perform(post("/api/books").contentType(MediaType.APPLICATION_JSON).content(bookJson))
				.andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();
		testBook = objectMapper.readValue(bookResponse, Book.class);
	}

	@Test
	void testBorrowAndReturnBook() throws Exception {
		// Borrow the book
		mockMvc.perform(post("/api/borrowers/" + testBorrower.getId() + "/books/" + testBook.getId() + "/borrow"))
				.andExpect(status().isOk());

		// Borrowing the same book again should fail with 409 Conflict
		mockMvc.perform(post("/api/borrowers/" + testBorrower.getId() + "/books/" + testBook.getId() + "/borrow"))
				.andExpect(status().isConflict());

		// Return the book
		mockMvc.perform(post("/api/borrowers/" + testBorrower.getId() + "/books/" + testBook.getId() + "/return"))
				.andExpect(status().isOk());

		// Returning the same book again should fail with 404 Not Found
		mockMvc.perform(post("/api/borrowers/" + testBorrower.getId() + "/books/" + testBook.getId() + "/return"))
				.andExpect(status().isNotFound());
	}

	@Test
	void testGetAllBooks() throws Exception {
		mockMvc.perform(get("/api/books")).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
				.andExpect(jsonPath("$[?(@.id == " + testBook.getId() + ")]").exists());
	}
}
