package com.library.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.entity.Borrower;

public interface BorrowerRepository extends JpaRepository<Borrower, Long> {
	Optional<Borrower> findByEmail(String email);
}
