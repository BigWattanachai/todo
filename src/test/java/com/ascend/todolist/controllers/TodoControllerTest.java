package com.ascend.todolist.controllers;

import com.ascend.todolist.entities.Todo;
import com.ascend.todolist.entities.TodoItem;
import com.ascend.todolist.services.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by BiG on 5/30/2017 AD.
 */
public class TodoControllerTest {
    @InjectMocks
    private TodoController controller;

    @Mock
    private TodoService todoService;
    private MockMvc mvc;
    private ObjectMapper mapper = new ObjectMapper();

    private Todo todo1;
    private Todo todo2;

    private TodoItem todoItem1;
    private TodoItem todoItem2;

    @Before
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();

        todo1 = new Todo();
        todo1.setContent("todo1");
        todo1.setId(1L);

        todo2 = new Todo();
        todo2.setId(2L);
        todo2.setContent("todo2");

        todoItem1 = new TodoItem();
        todoItem1.setId(1L);
        todoItem1.setComplete(false);
        todoItem1.setContent("item1");

        todoItem2 = new TodoItem();
        todoItem2.setId(1L);
        todoItem2.setComplete(true);
        todoItem2.setContent("item2");
    }

    @Test
    public void shouldReturnAllTodoWhenGetAllExistingTodoInDb() throws Exception {
        when(todoService.getAllTodo()).thenReturn(Arrays.asList(todo1, todo2));

        mvc.perform(get("/api/v1/todos"))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].content", is("todo1")))
                .andExpect(jsonPath("$[1].id").isNumber())
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].content", is("todo2")))
                .andExpect(status().isOk());

        verify(todoService).getAllTodo();
    }

    @Test
    public void shouldReturnTodoWhenCreateNewTodoSuccessfully() throws Exception {
        when(todoService.createTodo(any(Todo.class))).thenReturn(todo1);

        mvc.perform(post("/api/v1/todos")
                .content(mapper.writeValueAsString(todo1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.content", is("todo1")))
                .andExpect(status().isOk());

        verify(todoService).createTodo(any(Todo.class));
    }

    @Test
    public void shouldReturnTodoWhenGetExistingTodoById() throws Exception {
        when(todoService.getTodoById(anyLong())).thenReturn(todo1);

        mvc.perform(get("/api/v1/todos/1"))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.content", is("todo1")))
                .andExpect(status().isOk());

        verify(todoService).getTodoById(anyLong());
    }

    @Test
    public void shouldReturnTodoWhenUpdateExistingTodoSuccessfully() throws Exception {
        when(todoService.updateTodo(anyLong(), any(Todo.class))).thenReturn(todo1);

        mvc.perform(put("/api/v1/todos/1")
                .content(mapper.writeValueAsString(todo1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.content", is("todo1")))
                .andExpect(status().isOk());

        verify(todoService).updateTodo(anyLong(), any(Todo.class));
    }

    @Test
    public void shouldReturnTodoWhenDeleteExistingTodoInDb() throws Exception {
        when(todoService.deleteTodo(anyLong())).thenReturn(todo1);

        mvc.perform(delete("/api/v1/todos/1"))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.content", is("todo1")))
                .andExpect(status().isOk());

        verify(todoService).deleteTodo(anyLong());
    }

    @Test
    public void shouldReturnTodoItemWhenCreateTodoItemSuccessfully() throws Exception {
        when(todoService.createTodoItem(anyLong(), any(TodoItem.class))).thenReturn(todoItem1);

        mvc.perform(post("/api/v1/todos/1/items")
                .content(mapper.writeValueAsString(todoItem1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.content", is("item1")))
                .andExpect(status().isOk());

        verify(todoService).createTodoItem(anyLong(), any(TodoItem.class));
    }

    @Test
    public void shouldReturnTodoItemWhenGetExistingTodoItemWithId() throws Exception {
        when(todoService.getTodoItem(anyLong())).thenReturn(todoItem1);

        mvc.perform(get("/api/v1/todos/items/1"))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.content", is("item1")))
                .andExpect(status().isOk());

        verify(todoService).getTodoItem(anyLong());
    }
}
