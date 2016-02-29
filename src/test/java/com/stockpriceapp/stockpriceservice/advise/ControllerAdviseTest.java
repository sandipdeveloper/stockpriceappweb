package com.stockpriceapp.stockpriceservice.advise;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import javax.naming.NamingException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;

/**
 * Unit test for {@link ControllerAdvise}
 * @author sandip
 *
 */
public class ControllerAdviseTest {

	private ControllerAdvise controllerAdvise;
	
	@Before
	public void before() {
		controllerAdvise = new ControllerAdvise();
	}
	
	@Test
	public void testHandleNotFoundException() {
		ResponseEntity<Object> entity = controllerAdvise.handleNotFoundException(new NotFoundException("test"));
		assertEquals(entity.getStatusCode(), HttpStatus.NOT_FOUND);
		assertEquals(String.valueOf(entity.getBody()), "test");
	}
	
	@Test
	public void testHandleNamingException() {
		ResponseEntity<Object> entity = controllerAdvise. handleNamingException(new RuntimeException
				( new NamingException("test")));
		assertEquals(entity.getStatusCode(), HttpStatus.CONFLICT);
		assertTrue(String.valueOf(entity.getBody()).contains("test"));
	}
	
	@Test
	public void testHandleResourceAccessException() {
		ResponseEntity<Object> entity = controllerAdvise.handleResourceAccessException(new ResourceAccessException("test"));
		assertEquals(entity.getStatusCode(), HttpStatus.SERVICE_UNAVAILABLE);
		assertTrue(String.valueOf(entity.getBody()).contains("test"));
	}
	
	
	@Test
	public void testHandleBadRequestException() {
		ResponseEntity<Object> entity = controllerAdvise.handleBadRequestException( new BadRequestException("test"));
		assertEquals(entity.getStatusCode(), HttpStatus.BAD_REQUEST);
		assertTrue(String.valueOf(entity.getBody()).contains("test"));
	}
	
	@Test
	public void testHandleIOException() {
		ResponseEntity<Object> entity = controllerAdvise.handleIOException( new IOException("test"));
		assertEquals(entity.getStatusCode(), HttpStatus.SERVICE_UNAVAILABLE);
		assertTrue(String.valueOf(entity.getBody()).contains("test"));
	}
}