/**
 * 
 */
package com.sanal.controllers;


import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.talangsoft.rest.devtools.logging.Loggable;

import com.sanal.models.BookDTO;
import com.sanal.service.BookService;

/**
 * @author sanal567
 *
 */

@RestController
@RequestMapping("/api/books")
public class BookResourceImpl implements BookResource, Loggable {

	@Autowired
	BookService bookService;

	@GetMapping(value = "/{isbn}")
	public BookDTO getByIsbn(@PathVariable String isbn) {
		logger().info("Find book by isbn '{}'", isbn);
		return bookService.findByIsbn(isbn);
	}

	@GetMapping("/author")
	public List<BookDTO> getByAuthor(@RequestParam(value = "author", required = true) String author) {
		logger().info("Get book by author '{}'", author);
		return bookService.findByAuthor(author);
	}

	@GetMapping("/title")
	public List<BookDTO> getByTitle(@RequestParam(value = "title", required = true) String title) {

		logger().info("Get book by title like '{}'", title);
		return bookService.findByTitleLike(title);
	}

	@RequestMapping()
	public List<BookDTO> getAll() {
		logger().info("Get all books");
		return bookService.findAll();
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> createBook(@RequestBody @Valid BookDTO book) {
		logger().info("Create book {}", book);

		bookService.createNew(book);
		return new ResponseEntity<>("Book Saved Successfully", HttpStatus.CREATED);
	}

}
