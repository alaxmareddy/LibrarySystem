package com.library.service;

import org.springframework.stereotype.Service;

import com.library.entity.Book;
import com.library.entity.BorrowedBook;
import com.library.entity.Borrower;
import com.library.exception.BookAlreadyBorrowedException;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.BorrowedBookRepository;

@Service
public class BorrowingService {
	private final BorrowedBookRepository borrowedBookRepo;
	private final BookService bookService;
	private final BorrowerService borrowerService;

	public BorrowingService(BorrowedBookRepository borrowedBookRepo, BookService bookService,
			BorrowerService borrowerService) {
		this.borrowedBookRepo = borrowedBookRepo;
		this.bookService = bookService;
		this.borrowerService = borrowerService;
	}

	public void borrowBook(Long borrowerId, Long bookId) {
		Book book = bookService.getBookById(bookId);
		Borrower borrower = borrowerService.getBorrowerById(borrowerId);

		if (borrowedBookRepo.findByBook(book).isPresent()) {
			throw new BookAlreadyBorrowedException("Book with id " + bookId + " is already borrowed");
		}

		BorrowedBook borrowedBook = new BorrowedBook();
		borrowedBook.setBook(book);
		borrowedBook.setBorrower(borrower);
		borrowedBookRepo.save(borrowedBook);
	}

	public void returnBook(Long borrowerId, Long bookId) {
		Book book = bookService.getBookById(bookId);
		Borrower borrower = borrowerService.getBorrowerById(borrowerId);

		BorrowedBook borrowedBook = borrowedBookRepo.findByBook(book).orElseThrow(
				() -> new ResourceNotFoundException("Book with id " + bookId + " is not currently borrowed"));

		if (!borrowedBook.getBorrower().getId().equals(borrowerId)) {
			throw new IllegalArgumentException("This borrower did not borrow book with id " + bookId);
		}

		borrowedBookRepo.delete(borrowedBook);
	}
}
