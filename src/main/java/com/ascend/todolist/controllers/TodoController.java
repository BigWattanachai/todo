package com.ascend.todolist.controllers;

import com.ascend.todolist.entities.Todo;
import com.ascend.todolist.entities.TodoItem;
import com.ascend.todolist.services.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by BiG on 5/30/2017 AD.
 */
@RestController
@RequestMapping("/api/v1")
public class TodoController {
    private TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping("/todos")
    public List<Todo> getAllTodo() {
        return todoService.getAllTodo();
    }

    @PostMapping("/todos")
    public Todo createTodo(@Valid @RequestBody Todo todo) {
        return todoService.createTodo(todo);
    }

    @PutMapping("/todos/{id}")
    public Todo updateTodo(@PathVariable(value = "id") Long id, @Valid @RequestBody Todo todo) {
        return todoService.updateTodo(id, todo);
    }

    @GetMapping("/todos/{id}")
    public Todo getTodoById(@PathVariable(value = "id") Long id) {
        return todoService.getTodoById(id);
    }

    @DeleteMapping("/todos/{id}")
    public Todo deleteTodoById(@PathVariable(value = "id") Long id) {
        return todoService.deleteTodo(id);
    }

    @PostMapping("/todos/{todoId}/items")
    public ResponseEntity<TodoItem> createTodoItem(@PathVariable(value = "todoId") Long todoId, @Valid @RequestBody
            TodoItem todoItem) {
        return new ResponseEntity<>(todoService.createTodoItem(todoId, todoItem), HttpStatus.CREATED);
    }

    @GetMapping("/todos/items/{id}")
    public TodoItem getTodoItem(@PathVariable(value = "id") Long id) {
        return todoService.getTodoItem(id);
    }

    @DeleteMapping("/todos/items/{id}")
    public TodoItem deleteTodoItem(@PathVariable(value = "id") Long todoItem) {
        return todoService.deleteTodoItem(todoItem);
    }
}
