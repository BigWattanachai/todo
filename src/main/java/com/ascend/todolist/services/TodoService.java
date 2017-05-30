package com.ascend.todolist.services;

import com.ascend.todolist.entities.Todo;
import com.ascend.todolist.repositories.TodoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by BiG on 5/30/2017 AD.
 */
@Service
public class TodoService {
    private TodoRepo todoRepo;

    @Autowired
    public TodoService(TodoRepo todoRepo) {
        this.todoRepo = todoRepo;
    }

    public List<Todo> getAllTodo() {
        return todoRepo.findAll();
    }

    public Todo createTodo(Todo todo) {
        return todoRepo.saveAndFlush(todo);
    }
}
