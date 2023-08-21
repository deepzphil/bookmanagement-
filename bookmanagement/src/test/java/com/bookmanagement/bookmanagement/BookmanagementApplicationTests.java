package com.bookmanagement.bookmanagement;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.ArgumentMatchers.anyString;

import com.bookmanagement.bookmanagement.exception.BookNotFoundException;
import com.bookmanagement.bookmanagement.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.bookmanagement.bookmanagement.repository.BookRepository;
import com.bookmanagement.bookmanagement.service.BookServiceImpl;


@SpringBootTest
class BookmanagementApplicationTests {

	@Test
	void contextLoads() {
	}
	  @Mock
	    private BookRepository bookRepository;

	    @InjectMocks
	    private BookServiceImpl bookService;

	    @BeforeEach
	    void setUp() {
	        MockitoAnnotations.openMocks(this);
	    }

	    @Test
	    void testGetAllBooks() {
	        List<Book> bookList = new ArrayList<>();
	        bookList.add(new Book());
	        bookList.add(new Book());

	        // Create a Page<Book> from the bookList
	        Page<Book> mockPage = new PageImpl<>(bookList);

	        // Mock the findAll method to return the mockPage
	       when(bookRepository.findAll(any(Pageable.class))).thenReturn(mockPage);

	        // Call the service method
	        Page<Book> result = bookService.getAllBooks(PageRequest.of(0, 10));

	        assertEquals(2, result.getTotalElements()); 
	    }

	    @Test
	    void testGetBookById() {
	        Long bookId = 1L;
	        Book book = new Book();
	        book.setId(bookId);
             //Mock the findbyid to return book
	        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
             //call the service method
	        Book result = bookService.getBookById(bookId);

	        assertNotNull(result);
	        assertEquals(bookId, result.getId());
	    }

	    @Test
	    void testGetBookByIdNotFound() {
	        Long bookId = 1L;

	        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

	        assertThrows(BookNotFoundException.class, () -> bookService.getBookById(bookId));
	    }
	    @Test
	    void testCreateBook() {
	        Book bookToCreate = new Book();
	        bookToCreate.setTitle("Deep");
	        bookToCreate.setAuthor("Deepu");
	        bookToCreate.setPublicationYear(2020);
//mock the save method to save the book details
	        when(bookRepository.save(any(Book.class))).thenReturn(bookToCreate);

	        Book createdBook = bookService.createBook(bookToCreate);

	        assertNotNull(createdBook);
	        assertEquals("Deep", createdBook.getTitle());

	    }
	    @Test
	    void testUpdateBook() {
	        Long bookId = 1L;
	        Book existingBook = new Book();
	        existingBook.setId(bookId);
	        existingBook.setTitle("deep");
	        existingBook.setAuthor("deepu");
	        existingBook.setPublicationYear(2020);

	        Book updatedBook = new Book();
	        updatedBook.setId(bookId);
	        updatedBook.setTitle("dee");
	        updatedBook.setAuthor("dee");
	        updatedBook.setPublicationYear(2022);
//Mock the findbyid and return the existing book
	        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
	        //updating the existing book
	        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

	        Book result = bookService.updateBook(bookId, updatedBook);

	        assertNotNull(result);
	        assertEquals("dee", result.getTitle());//whether the title is dee
	        assertEquals("dee", result.getAuthor());//whether author is dee
	        assertEquals(2022, result.getPublicationYear());//whether year is 2022
	    }
	    @Test
	    void testSearchBooksByTitle() {
	       
	        Book book1 = new Book();
	        book1.setId(1L);
	        book1.setTitle("Book");
	        book1.setAuthor("Dan");
	        book1.setPublicationYear(2020);

	        Book book2 = new Book();
	        book2.setId(2L);
	        book2.setTitle("Book");
	        book2.setAuthor("James");
	        book2.setPublicationYear(2022);

	        List<Book> matchingBooks = new ArrayList<>();
	        matchingBooks.add(book1);
	        matchingBooks.add(book2);

	        when(bookRepository.findByTitle(anyString())).thenReturn(matchingBooks);
// we are searching a book using the title
	        List<Book> searchResults = bookService.searchBooks(book1.getTitle());

	        assertNotNull(searchResults);
	        assertEquals(2, searchResults.size());// we get two books as title is same
	        assertEquals("Book", searchResults.get(0).getTitle());
	        assertEquals("Book", searchResults.get(1).getTitle());
	    }

	    @Test
	    void testSearchBooksByTitleNotFound() {
	        String searchTitle = "Book3";

	        when(bookRepository.findByTitle(anyString())).thenReturn(new ArrayList<>());

	        List<Book> searchResults = bookService.searchBooks(searchTitle);

	        assertNotNull(searchResults);
	        assertTrue(searchResults.isEmpty());// it would be empty as titile is not found
	    }
	}

