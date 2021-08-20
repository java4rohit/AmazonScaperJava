package com.aihello.amazon.controller;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.aihello.amazon.model.ErrorMessage;

@ControllerAdvice
public class ExceptionHandlerController {

	  @ExceptionHandler(value = {IllegalArgumentException.class})
	  public ResponseEntity<ErrorMessage> resourceNotFoundException(IllegalArgumentException ex, WebRequest request) {
	    ErrorMessage message = new ErrorMessage(
	        401,
	        new Date(),
	        "Illegal Argument",
	        ex.getMessage());
	    
	    return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
	  }
	
}
