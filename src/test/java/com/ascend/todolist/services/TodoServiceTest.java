package com.ascend.todolist.services;

import com.ascend.todolist.entities.Todo;
import com.ascend.todolist.repositories.TodoRepo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by BiG on 5/30/2017 AD.
 */
public class TodoServiceTest {
    @InjectMocks
    private TodoService todoService;

    @Mock
    private TodoRepo todoRepo;

    private Todo todo1;
    private Todo todo2;

    @Before
    public void setUpBeforeEach() {
        MockitoAnnotations.initMocks(this);
        todoService = new TodoService(todoRepo);

        todo1 = new Todo();
        todo1.setContent("todo1");
        todo1.setId(1L);
        todo2 = new Todo();
        todo2.setId(2L);
        todo2.setContent("todo2");
    }


    @Test
    public void shouldReturnTodoWhenCreateNewTodoSuccessfully() throws Exception {
        when(todoRepo.saveAndFlush(Matchers.any(Todo.class))).thenReturn(todo1);

        Todo TodoCreated = todoService.createTodo(todo1);
        assertThat(TodoCreated.getContent(), is("todo1"));
        assertThat(TodoCreated.getId(), is(1L));

        verify(todoRepo).saveAndFlush(Matchers.any(Todo.class));
    }

    @Test
    public void shouldReturnAllTodoWhenGetAllExistingTodoInDb() throws Exception {
        when(todoRepo.findAll()).thenReturn(Arrays.asList(todo1, todo2));

        List<Todo> TodoList = todoService.getAllTodo();
        assertThat(TodoList.size(), is(2));
        assertThat(TodoList.get(0).getId(), is(1L));
        assertThat(TodoList.get(0).getContent(), is("todo1"));
        assertThat(TodoList.get(1).getId(), is(2L));
        assertThat(TodoList.get(1).getContent(), is("todo2"));

        verify(todoRepo).findAll();
    }

}
