package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.Status;
import com.example.demo.dto.TodoDto;
import com.example.demo.entity.Todo;
import com.example.demo.service.api.ITodoService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/todos")
public class TodoController {

	private final ITodoService todoService;
	
	@GetMapping
	public List<Todo> listTodos(){
		return todoService.getAllTodos();
	}
	
	@GetMapping("/{id}")
	public Todo getTodoById(@PathVariable("id") Long id) {
		return todoService.findById(id);
	}
	
	@GetMapping("/name/{name}")
	public Todo getTodoByName(@PathVariable("name") String name) {
		return todoService.findByName(name);
	}
	
	@PostMapping
	public ResponseEntity<Todo> addTodo(@RequestBody TodoDto todoDto) {
		Todo newRecord = todoService.add(todoDto);
		return new ResponseEntity<Todo>(newRecord, HttpStatus.CREATED);
	}
	
	@PutMapping
	public Todo updateTodo(@RequestParam("name") String name, @RequestParam("status") Status newStatus) {
		return todoService.updateStatus(name, newStatus);
	}
	
	@DeleteMapping("/{id}")
	public void deleteTodoById(@PathVariable("id") Long id) {
		todoService.deleteById(id);
	}
	
}
