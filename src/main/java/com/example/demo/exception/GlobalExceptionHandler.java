package com.example.demo.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException e){
		
		TodoExceptionInfo errorInfo = TodoExceptionInfo.builder()
				.status(HttpStatus.NOT_FOUND)
				.error(e.getMessage())
				.timeStamp(LocalDateTime.now())
				.build();
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorInfo);
	}
}
