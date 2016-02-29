package com.stockpriceapp.stockpriceservice.advise;

import java.io.IOException;

import javax.naming.NamingException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Global exception handler for controllers
 * @author sandip
 *
 */
@ControllerAdvice
public class ControllerAdvise extends ResponseEntityExceptionHandler {

	@ExceptionHandler
	public ResponseEntity<Object> handleNotFoundException(NotFoundException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	public ResponseEntity<Object> handleNamingException(RuntimeException exception) {
		if(exception.getCause() instanceof NamingException) {
			return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
		} else {
			throw exception;
		}
	}
	
	@ExceptionHandler
	public ResponseEntity<Object> handleResourceAccessException(ResourceAccessException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	@ExceptionHandler
	public ResponseEntity<Object> handleIOException(IOException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	@ExceptionHandler
	public ResponseEntity<Object> handleBadRequestException(BadRequestException exception) {
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
}
