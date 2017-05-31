package com.ascend.todolist.repositories;

import com.ascend.todolist.entities.TodoItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by BiG on 5/31/2017 AD.
 */
public interface TodoItemRepo extends JpaRepository<TodoItem, Long> {
}
