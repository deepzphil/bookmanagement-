package com.bookmanagement.bookmanagement.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

//custom exception when book is not found
@ResponseStatus(HttpStatus.NOT_FOUND)
	public class BookNotFoundException extends RuntimeException {
	    public BookNotFoundException(String message) {
	        super(message);
	    }

}
