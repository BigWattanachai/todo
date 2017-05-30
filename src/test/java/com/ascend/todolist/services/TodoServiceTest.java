package com.ascend.todolist.services;

import com.ascend.todolist.entities.Todo;
import com.ascend.todolist.exceptions.TodoNotFoundException;
import com.ascend.todolist.repositories.TodoRepo;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
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

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

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

    @Test
    public void shouldReturnTodoWhenGetExistingTodoInDbWithId() throws Exception {
        when(todoRepo.findOne(anyLong())).thenReturn(todo1);

        Todo TodoResponse = todoService.getTodoById(1L);
        assertThat(TodoResponse.getContent(), is("todo1"));
        assertThat(TodoResponse.getId(), is(1L));

        verify(todoRepo).findOne(anyLong());
    }

    @Test
    public void shouldThrowTodoExceptionWhenGetNonExistTodoInDb() throws Exception {
        when(todoRepo.findOne(anyLong())).thenReturn(null);

        expectedEx.expect(TodoNotFoundException.class);
        expectedEx.expectMessage("Todo id 1 is not found");
        todoService.getTodoById(1L);

        verify(todoRepo).findOne(anyLong());
    }

    @Test
    public void shouldReturnTodoWhenUpdateExistingTodoSuccessfully() throws Exception {
        when(todoRepo.findOne(anyLong())).thenReturn(todo1);
        when(todoRepo.saveAndFlush(any(Todo.class))).thenReturn(todo1);

        Todo TodoResponse = todoService.updateTodo(1L, todo1);
        assertThat(TodoResponse.getId(), is(1L));
        assertThat(TodoResponse.getContent(), is("todo1"));

        verify(todoRepo).findOne(anyLong());
        verify(todoRepo).saveAndFlush(any(Todo.class));
    }

    @Test
    public void shouldThrowTodoExceptionWhenUpdateNonExistTodoInDb() throws Exception {
        when(todoRepo.findOne(anyLong())).thenReturn(null);

        expectedEx.expect(TodoNotFoundException.class);
        expectedEx.expectMessage("Todo id 1 is not found");
        todoService.updateTodo(1L, todo1);

        verify(todoRepo).findOne(anyLong());
        verify(todoRepo, never()).saveAndFlush(any(Todo.class));
    }

    @Test
    public void shouldReturnTodoWhenDeleteExistingTodoInDbWithId() throws Exception {
        when(todoRepo.findOne(anyLong())).thenReturn(todo1);
        doNothing().when(todoRepo).delete(Matchers.any(Todo.class));

        Todo TodoResponse = todoService.deleteTodo(1L);
        assertThat(TodoResponse.getId(), is(1L));
        assertThat(TodoResponse.getContent(), is("todo1"));

        verify(todoRepo).findOne(anyLong());
        verify(todoRepo).delete(Matchers.any(Todo.class));
    }

    @Test
    public void shouldThrowTodoExceptionWhenDeleteNonExistTodoInDb() throws Exception {
        when(todoRepo.findOne(anyLong())).thenReturn(null);

        expectedEx.expect(TodoNotFoundException.class);
        expectedEx.expectMessage("Todo id 1 is not found");
        todoService.deleteTodo(1L);

        verify(todoRepo).findOne(anyLong());
        verify(todoRepo, never()).delete(anyLong());
    }
}
