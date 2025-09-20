package com.library.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.library.entity.Borrower;
import com.library.service.BorrowerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/borrowers")
public class BorrowerController {

	private final BorrowerService borrowerService;

	public BorrowerController(BorrowerService borrowerService) {
		this.borrowerService = borrowerService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Borrower registerBorrower(@Valid @RequestBody Borrower borrower) {
		return borrowerService.registerBorrower(borrower);
	}
}
