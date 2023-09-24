package com.example.demo.exception;

public class ResourceNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1708236615905032839L;
	
	public ResourceNotFoundException(String message) {
		super(message);
	}
	
	public ResourceNotFoundException(String message, Throwable exception) {
		super(message, exception);
	}
}
