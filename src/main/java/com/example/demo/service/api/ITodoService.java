package com.example.demo.service.api;

import java.util.List;

import com.example.demo.dto.Status;
import com.example.demo.dto.TodoDto;
import com.example.demo.entity.Todo;

public interface ITodoService {
	Todo add(TodoDto todoDto);
	
	Todo updateStatus(String name, Status newStatus);
	
	Todo findByName(String name);
	Todo findById(long id);
	List<Todo> getAllTodos();
	
	void deleteById(long id);
}
