package com.sanal.service;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.talangsoft.rest.devtools.logging.Loggable;
import org.talangsoft.rest.devtools.web.RestException;
import org.talangsoft.rest.devtools.web.util.PNV;
import org.talangsoft.rest.devtools.web.util.RestUtils;

import com.sanal.models.Book;
import com.sanal.models.BookDTO;
import com.sanal.models.RestErrors;
import com.sanal.repository.BookRepository;

@Service
public class BookServiceImpl implements Loggable, BookService {


    @Autowired
    BookRepository bookRepo;

    private Function<Book,BookDTO> bookToBookDTO = book-> new BookDTO(book.getIsbn(), book.getTitle(),book.getAuthor());

    private BiFunction<String, BookDTO, Book> bookDTOToBook = (isbn,bookDTO) -> new Book(isbn,bookDTO.getTitle(),bookDTO.getAuthor());

    @Override
    @Transactional
    public String createNew(BookDTO bookDTO){
        logger().debug("Create new book {}",bookDTO);
     // String isbn = isbnGateway.registerBook(bookDTO);
        bookRepo.save( bookDTOToBook.apply(isbn, bookDTO));
        return isbn;
    }

    @Override
    @Transactional
    public void update(String isbn, BookDTO bookDTO){
        logger().debug("Update book for isbn '{}' : {}",isbn, bookDTO);
        Book retrievedBook = bookRepo.findOne(isbn);
        retrievedBook.setAuthor(bookDTO.getAuthor());
        retrievedBook.setTitle(bookDTO.getTitle());
        bookRepo.save(retrievedBook);
    }


    @Override
    public BookDTO findByIsbn(String isbn){
        logger().debug("Find by isbn '{}'",isbn);
        Book retrievedBook = bookRepo.findOne(isbn);
        if(retrievedBook == null){
            throw new RestException(RestErrors.BOOK_NOT_FOUND.toRestError(),
                    RestUtils.createParams(PNV.toPNV("isbn",isbn)));
        }
        return bookToBookDTO.apply(retrievedBook);

    }

    @Override
    public List<BookDTO> findByAuthor(String author){
        logger().debug("Find by author '{}'",author);
        return bookRepo.findByAuthor(author).stream().map(bookToBookDTO).collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> findByTitleLike(String title){
        logger().debug("Find by title '{}'",title);
        return bookRepo.findByTitleLike(title).stream().map(bookToBookDTO).collect(Collectors.toList());
    }

    @Override
    public List<BookDTO> findAll(){
        logger().debug("Find all");
        return bookRepo.findAll().stream().map(bookToBookDTO).collect(Collectors.toList());
    }

    @Override
    public void delete(String isbn){
        logger().debug("Delete book with isbn '{}'",isbn);
        bookRepo.delete(isbn);
    }
    
    @Override
    public void deleteByTitle(String title){
        logger().debug("Delete book with title '{}'",title);
        bookRepo.deleteByTitle(title);
    }