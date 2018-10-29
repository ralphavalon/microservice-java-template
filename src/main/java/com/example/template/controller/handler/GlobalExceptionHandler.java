package com.example.template.controller.handler;

import java.util.HashMap;

import javax.ws.rs.NotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ErrorMessage exception(Exception e) {
		var error = "Internal Server Error";
		var message = "Unexpected server error";
		return errorMessage(error, message);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ServletRequestBindingException.class)
	public ErrorMessage missingRequestAttribute(ServletRequestBindingException ex) {
		var error = "Wrong or missing parameters";
		var message = ex.getMessage();
		return errorMessage(error, message);
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorMessage handleNotFound(NotFoundException e) {
		return errorMessage("not found", 
				String.format("cannot found resource: '%s'", e.getMessage()));
    }
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorMessage notValid(MethodArgumentNotValidException e) {
		var invalidFieldMessage = new HashMap<String, String>();
		e.getBindingResult().getFieldErrors()
				.forEach(error -> invalidFieldMessage.put(error.getField(), error.getDefaultMessage()));
		var error = "Invalid Request";
		var message = "Invalid Field(s)";
		return errorMessage(error, message, invalidFieldMessage);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ErrorMessage notReadable(HttpMessageNotReadableException e) {
		var error = "Invalid Request";
		var message = "Unacceptable request body";
		return errorMessage(error, message);
	}
	
	private ErrorMessage errorMessage(String error, String message) {
		return errorMessage(error, message, null);
	}

	private ErrorMessage errorMessage(String error, String message, Object details) {
		return ErrorMessage.builder().error(error).message(message).details(details).build();
	}
	
	@Getter
	@Builder
	static class ErrorMessage {
		
		@NonNull
		private String error;
		@NonNull
		private String message;
		@JsonInclude(JsonInclude.Include.NON_NULL)
		private Object details;
		
	}
	
}
