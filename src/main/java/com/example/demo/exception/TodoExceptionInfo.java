package com.example.demo.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TodoExceptionInfo {
	private HttpStatus status;
	private String error;
	private LocalDateTime timeStamp;
}
