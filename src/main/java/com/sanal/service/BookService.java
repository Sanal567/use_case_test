package com.sanal.service;

import java.util.List;

import javax.transaction.Transactional;

import com.sanal.models.BookDTO;

public interface BookService {
	@Transactional
	String createNew(BookDTO bookDTO);

	@Transactional
	void update(String isbn, BookDTO bookDTO);

	BookDTO findByIsbn(String isbn);

	List<BookDTO> findByAuthor(String author);

	List<BookDTO> findByTitleLike(String title);

	List<BookDTO> findAll();

	void delete(String isbn);
}