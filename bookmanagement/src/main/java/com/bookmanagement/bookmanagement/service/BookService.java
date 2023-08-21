package com.bookmanagement.bookmanagement.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.bookmanagement.bookmanagement.model.Book;
//Interface bookservice
public interface BookService {
	//To return all books in pagination
   Page<Book> getAllBooks(Pageable pageable);
   
   // to return book by a id
    Book getBookById(Long id);
    
   // to insert a book
    Book createBook(Book book);
    
   //to update a book
    Book updateBook(Long id, Book book);
    
   //to delete a book
    void deleteBook(Long id);
    
   //to search a book
    List<Book> searchBooks(String author);

}
