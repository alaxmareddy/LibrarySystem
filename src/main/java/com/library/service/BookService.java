package com.library.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.library.entity.Book;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.BookRepository;

@Service
public class BookService {
	private final BookRepository bookRepo;

	public BookService(BookRepository bookRepo) {
		this.bookRepo = bookRepo;
	}

	public Book registerBook(Book book) {
		// Enforce ISBN consistency:
		bookRepo.findByIsbn(book.getIsbn()).ifPresent(existing -> {
			if (!existing.getTitle().equals(book.getTitle()) || !existing.getAuthor().equals(book.getAuthor())) {
				throw new IllegalArgumentException("ISBN conflict: same ISBN but different title/author");
			}
		});
		return bookRepo.save(book);
	}

	public List<Book> getAllBooks() {
		return bookRepo.findAll();
	}

	public Book getBookById(Long id) {
		return bookRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
	}
}
