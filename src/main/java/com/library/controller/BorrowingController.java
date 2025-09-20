package com.library.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.library.service.BorrowingService;

@RestController
@RequestMapping("/api/borrowers/{borrowerId}/books")
public class BorrowingController {

	private final BorrowingService borrowingService;

	public BorrowingController(BorrowingService borrowingService) {
		this.borrowingService = borrowingService;
	}

	@PostMapping("/{bookId}/borrow")
	@ResponseStatus(HttpStatus.OK)
	public void borrowBook(@PathVariable Long borrowerId, @PathVariable Long bookId) {
		borrowingService.borrowBook(borrowerId, bookId);
	}

	@PostMapping("/{bookId}/return")
	@ResponseStatus(HttpStatus.OK)
	public void returnBook(@PathVariable Long borrowerId, @PathVariable Long bookId) {
		borrowingService.returnBook(borrowerId, bookId);
	}
}
