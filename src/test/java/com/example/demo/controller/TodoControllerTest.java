package com.example.demo.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.example.demo.dto.Status;
import com.example.demo.dto.TodoDto;
import com.example.demo.entity.Todo;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.api.ITodoService;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(TodoController.class)
public class TodoControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private ITodoService todoService;
	
	private ObjectMapper objectMapper= new ObjectMapper();
	
	@Test
	void testDelete() throws Exception {
		mvc.perform(delete("/todos/{id}", 101)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk());
		
		//verify(todoService).deleteById(101);
	}
	
	@Test
	void testAdd() throws Exception {
		TodoDto todo = TodoDto.builder().name("tt").build();
		
		mvc.perform(post("/todos")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(todo)))
		.andExpect(status().isCreated());
	}
	
	@Test
	void testGetById_Failure() throws Exception {
		when(todoService.findById(1L)).thenThrow(new ResourceNotFoundException("not found"));
		//when & then
		mvc.perform(get("/todos/{id}", 1L).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isNotFound())
		.andExpect(jsonPath("$.error").exists());
	}
	
	@Test
	void testGetById() throws Exception {
		when(todoService.findById(100L)).thenReturn(Todo.builder().id(100L).build());
		//when & then
		mvc.perform(get("/todos/{id}", 100L).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id").exists());
	}
	
	@Test
	void testGetByName() throws Exception {
		when(todoService.findByName("test")).thenThrow(new ResourceNotFoundException("not found"));
		//when & then
		ResultActions result = mvc.perform(get("/todos/name/{name}", "test").contentType(MediaType.APPLICATION_JSON));
		result.andExpect(status().isNotFound());
		
		//.andExpect(jsonPath("$.error").exists());
	}

	@Test
	void testListTodos() throws Exception {
		Todo t1 = getTestRecord();
		
		Mockito.when(todoService.getAllTodos()).thenReturn(Arrays.asList(t1));
		
		mvc.perform(get("/todos").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].name").value("exam"))
				.andExpect(jsonPath("$[0].active").isBoolean())
				.andExpect(jsonPath("$[0].targetDate").exists());
				
	}

	private Todo getTestRecord() {
		Todo t1 = Todo.builder()
				.id(1L)
				.name("exam")
				.currentStatus(Status.IN_PROGRESS)
				.targetDate(LocalDate.now())
				.active(true)
				.build();
		return t1;
	}
}
