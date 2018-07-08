package com.example.template.controller.handler;

import javax.ws.rs.NotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.Getter;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorMessage handleNotFound(NotFoundException e) {
		return new ErrorMessage("not found", 
				String.format("cannot found resource: '%s'", e.getMessage()));
    }
	
	@Getter
	class ErrorMessage {
		String error;
		String message;
		
		ErrorMessage(String error, String message) {
			this.error = error;
			this.message = message;
		}
		
	}
	
}
