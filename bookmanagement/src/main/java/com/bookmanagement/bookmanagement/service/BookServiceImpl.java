package com.bookmanagement.bookmanagement.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bookmanagement.bookmanagement.exception.BookNotFoundException;
import com.bookmanagement.bookmanagement.model.Book;
import com.bookmanagement.bookmanagement.repository.BookRepository;


@Service
public class BookServiceImpl implements BookService{
	@Autowired
	BookRepository bookRepository;
//to return all books in pagination
	@Override
	public Page<Book> getAllBooks( Pageable pageable) {
		 return bookRepository.findAll(pageable);
	}
// to return book by id
	@Override
	public Book getBookById(Long id) {
		
	            return bookRepository.findById(id).orElseThrow(
	                () -> new BookNotFoundException("Book not found with id: " + id)
	            );
	        
	}
// to create/savea book
	@Override
	public Book createBook(Book book) {
		  try {
	            return bookRepository.save(book);
	        } catch (Exception ex) {
	           throw new RuntimeException("Unexpected error while creating a book", ex);
	        }
	}
// to update a book
	@Override
	public Book updateBook(Long id, Book book) {
		
			 //to check whether a existing book is there 
	            Optional<Book> existingBook = bookRepository.findById(id);
	            //if so update the book
	            if (existingBook.isPresent()) {
	                book.setId(id);
	                return bookRepository.save(book);
	            } else {
	                throw new BookNotFoundException("Book not found with id: " + id);
	            }

	}
// to delete a book
	@Override
	public void deleteBook(Long id) {
		 
			  Optional<Book> existingBook = bookRepository.findById(id);
			  if (existingBook.isPresent()) {
	            bookRepository.deleteById(id);
			  } else {
				  throw new BookNotFoundException("Book not found with id: " + id);
			  }
	      
	}
	//to search a book
	@Override
	public List<Book> searchBooks(String title) {
		try {
	        List<Book> foundBooks = bookRepository.findByTitle(title);
	        return foundBooks;
	    } catch (Exception ex) {
	        throw new RuntimeException("Book not found with title:" , ex);
	    }
	}

}
