package com.bookmanagement.bookmanagement.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bookmanagement.bookmanagement.exception.BookNotFoundException;
import com.bookmanagement.bookmanagement.model.Book;
import com.bookmanagement.bookmanagement.service.BookService;

@RestController
	@RequestMapping("/api/books")
	public class BookController {
	    private final BookService bookService;

	    @Autowired
	    public BookController(BookService bookService) {
	        this.bookService = bookService;
	    }
//to get all the books using pagination
	    //pagination size to be 10
	    @GetMapping
	    public ResponseEntity<Page<Book>> getAllBooks(@PageableDefault(size = 10) Pageable pageable) {
	        Page<Book> books = bookService.getAllBooks(pageable);
	        return ResponseEntity.ok(books);
	    }
// to get book using id
	    @GetMapping("/{id}")
	    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
	        Book book = bookService.getBookById(id);
	        if (book != null) {
	            return ResponseEntity.ok(book);
	        } else {
	            throw new BookNotFoundException("Book not found with id: " + id);
	        }
	    }
// to save book related details
	    @PostMapping
	    public ResponseEntity<Book> createBook(@RequestBody Book book) {
	        Book createdBook = bookService.createBook(book);
	        return ResponseEntity.ok(createdBook);
	    }
// to update a book related details
	    @PutMapping("/{id}")
	    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
	        Book updatedBook = bookService.updateBook(id, book);
	        if (updatedBook != null) {
	            return ResponseEntity.ok(updatedBook);
	        } else {
	            throw new BookNotFoundException("Book not found with id: " + id);
	        }
	    }
// to delete a book
	    @DeleteMapping("/{id}")
	    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
	        bookService.deleteBook(id);
	        return ResponseEntity.ok("the book is deleted");
	    	}
// to search a book using title
	    @GetMapping("/search")
	    public ResponseEntity<List<Book>> searchBooks(@RequestParam String title) {
	        List<Book> books = bookService.searchBooks(title);
	        return ResponseEntity.ok(books);
	    }

	    @ExceptionHandler(BookNotFoundException.class)
	    public ResponseEntity<String> handleBookNotFoundException(BookNotFoundException ex) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	    }
	    
	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<String> handleException(Exception ex) {
	       return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + ex.getMessage());
	    }
	

}
