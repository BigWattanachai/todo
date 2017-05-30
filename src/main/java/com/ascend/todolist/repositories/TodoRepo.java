package com.ascend.todolist.repositories;

import com.ascend.todolist.entities.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by BiG on 5/30/2017 AD.
 */
public interface TodoRepo extends JpaRepository<Todo, Long> {
}
