package com.library.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.library.entity.Book;
import com.library.entity.BorrowedBook;

public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Long> {
	Optional<BorrowedBook> findByBook(Book book);

	Optional<BorrowedBook> findByBorrowerIdAndBookId(Long id, Long id2);
}
