package com.example.demo.dto;

import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TodoDto {

	private String name;
	
	private LocalDate targetDate;
	
	@Enumerated(EnumType.STRING)
	private Status currentStatus;
}
