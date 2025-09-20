package com.library.service;

import org.springframework.stereotype.Service;

import com.library.entity.Borrower;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.BorrowerRepository;

@Service
public class BorrowerService {
	private final BorrowerRepository borrowerRepo;

	public BorrowerService(BorrowerRepository borrowerRepo) {
		this.borrowerRepo = borrowerRepo;
	}

	public Borrower registerBorrower(Borrower borrower) {
		return borrowerRepo.save(borrower);
	}

	public Borrower getBorrowerById(Long id) {
		return borrowerRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Borrower not found with id: " + id));
	}
}
