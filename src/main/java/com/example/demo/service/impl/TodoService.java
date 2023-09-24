package com.example.demo.service.impl;

import java.text.MessageFormat;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.example.demo.dto.Status;
import com.example.demo.dto.TodoDto;
import com.example.demo.entity.Todo;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repsitory.TodoRepository;
import com.example.demo.service.api.ITodoService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TodoService implements ITodoService {
	
	private final TodoRepository todoRepository;

	@Override
	public Todo add(TodoDto todoDto) {
		Todo todoEntity = new Todo();
		BeanUtils.copyProperties(todoDto, todoEntity);
		return todoRepository.save(todoEntity); 
	}

	@Override
	public Todo updateStatus(String name, Status newStatus) {
		Todo todo = todoRepository.findByName(name);
		if(todo == null) {
			String errorMsg = MessageFormat.format("Task name {0} not found", name);
			throw new ResourceNotFoundException(errorMsg);
		}
		
		todo.setCurrentStatus(newStatus);
		return todoRepository.save(todo);
	}

	@Override
	public Todo findByName(String name) {
		Todo todo = todoRepository.findByName(name);
		if(todo == null) {
			String errorMsg = MessageFormat.format("Task name {0} not found", name);
			throw new ResourceNotFoundException(errorMsg);
		}
		
		return todo;
	}

	@Override
	public Todo findById(long id) {
		String errorMsg = MessageFormat.format("Task ID {0} not found", id);
		return todoRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(errorMsg));
	}

	@Override
	public List<Todo> getAllTodos() {
		return todoRepository.findAll();
	}

	@Override
	public void deleteById(long id) {
		todoRepository.deleteById(id);
	}

}
