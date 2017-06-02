package com.ascend.todolist.services;

import com.ascend.todolist.constants.ErrorMsgEnum;
import com.ascend.todolist.entities.Todo;
import com.ascend.todolist.entities.TodoItem;
import com.ascend.todolist.exceptions.TodoNotFoundException;
import com.ascend.todolist.repositories.TodoItemRepo;
import com.ascend.todolist.repositories.TodoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by BiG on 5/30/2017 AD.
 */
@Service
public class TodoService {
    private TodoRepo todoRepo;

    private TodoItemRepo todoItemRepo;

    @Autowired
    public TodoService(TodoRepo todoRepo, TodoItemRepo todoItemRepo) {
        this.todoRepo = todoRepo;
        this.todoItemRepo = todoItemRepo;
    }

    public List<Todo> getAllTodo() {
        return todoRepo.findAll();
    }

    public Todo createTodo(Todo todo) {
        return todoRepo.saveAndFlush(todo);
    }

    public Todo updateTodo(Long id, Todo todoUpdate) {
        Todo todo = Optional.ofNullable(todoRepo.findOne(id))
                .orElseThrow(() -> new TodoNotFoundException(String.format(ErrorMsgEnum.TODO_NOT_FOUND.getMsg(), id)));
        todo.setContent(todoUpdate.getContent());
        return todoRepo.saveAndFlush(todo);
    }

    public Todo getTodoById(Long id) {
        return Optional.ofNullable(todoRepo.findOne(id))
                .orElseThrow(() -> new TodoNotFoundException(String.format(ErrorMsgEnum.TODO_NOT_FOUND.getMsg(), id)));
    }

    public Todo deleteTodo(Long id) {
        Todo todo = Optional.ofNullable(todoRepo.findOne(id))
                .orElseThrow(() -> new TodoNotFoundException(String.format(ErrorMsgEnum.TODO_NOT_FOUND.getMsg(), id)));
        todoRepo.delete(todo);
        return todo;
    }

    public TodoItem createTodoItem(Long todoId, TodoItem todoItem) {
        Todo todo = Optional.ofNullable(todoRepo.findOne(todoId)).orElseThrow(() ->
                new TodoNotFoundException(String.format(ErrorMsgEnum.TODO_NOT_FOUND.getMsg(), todoId)));

        todoItem.setTodo(todo);
        return todoItemRepo.saveAndFlush(todoItem);
    }

    public TodoItem getTodoItem(Long id) {
        return Optional.ofNullable(todoItemRepo.findOne(id)).orElseThrow(() ->
                new TodoNotFoundException(String.format(ErrorMsgEnum.TODO_ITEM_NOT_FOUND.getMsg(), id)));
    }

    public TodoItem deleteTodoItem(Long id) {
        TodoItem todoItem = Optional.ofNullable(todoItemRepo.findOne(id)).orElseThrow(() ->
                new TodoNotFoundException(String.format(ErrorMsgEnum.TODO_ITEM_NOT_FOUND.getMsg(), id)));
        todoItemRepo.delete(todoItem);
        return todoItem;

    }
}
