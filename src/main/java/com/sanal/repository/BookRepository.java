package com.sanal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanal.models.Book;

public interface BookRepository extends JpaRepository<Book, String> {
	
	List<Book> findByAuthor(String author);

	List<Book> findByTitleLike(String title);
	
	List<Book> findByISBN(String isbn);
	
	void deleteByTitle(String title);
}
