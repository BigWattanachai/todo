package com.ascend.todolist.services;

import com.ascend.todolist.entities.Todo;
import com.ascend.todolist.entities.TodoItem;
import com.ascend.todolist.exceptions.TodoNotFoundException;
import com.ascend.todolist.repositories.TodoItemRepo;
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
import static org.junit.Assert.assertFalse;
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

    @Mock
    private TodoItemRepo todoItemRepo;

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    private Todo todo1;
    private Todo todo2;

    private TodoItem todoItem1;

    @Before
    public void setUpBeforeEach() {
        MockitoAnnotations.initMocks(this);
        todoService = new TodoService(todoRepo, todoItemRepo);

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

    @Test
    public void shouldReturnTodoItemWhenCreateTodoItemSuccessfully() throws Exception {
        when(todoRepo.findOne(anyLong())).thenReturn(todo1);
        when(todoItemRepo.saveAndFlush(any(TodoItem.class))).thenReturn(todoItem1);

        TodoItem todoItem = todoService.createTodoItem(1L, todoItem1);
        assertThat(todoItem.getId(), is(1L));
        assertThat(todoItem.getContent(), is("item1"));
        assertFalse(todoItem.getComplete());

        verify(todoRepo).findOne(anyLong());
        verify(todoItemRepo).saveAndFlush(any(TodoItem.class));
    }

    @Test
    public void shouldThrowTodoItemExceptionWhenCreateItemWithNonExistTodoInDb() throws Exception {
        when(todoRepo.findOne(anyLong())).thenReturn(null);

        expectedEx.expect(TodoNotFoundException.class);
        expectedEx.expectMessage("Todo id 1 is not found");
        todoService.createTodoItem(1L, todoItem1);

        verify(todoRepo).findOne(anyLong());
        verify(todoItemRepo, never()).saveAndFlush(any(TodoItem.class));
    }

    @Test
    public void shouldReturnTodoItemWhenGetExistingTodoItemWithId() throws Exception {
        when(todoItemRepo.findOne(anyLong())).thenReturn(todoItem1);

        TodoItem todoItem = todoService.getTodoItem(1L);
        assertThat(todoItem.getId(), is(1L));
        assertThat(todoItem.getContent(), is("item1"));
        assertFalse(todoItem.getComplete());

        verify(todoItemRepo).findOne(anyLong());
    }

    @Test
    public void shouldThrowTodoItemExceptionWhenGetNonExistItemInDb() throws Exception {
        when(todoItemRepo.findOne(anyLong())).thenReturn(null);

        expectedEx.expect(TodoNotFoundException.class);
        expectedEx.expectMessage("Todo item id 1 is not found");
        todoService.getTodoItem(1L);

        verify(todoItemRepo).findOne(anyLong());
    }

    @Test
    public void shouldReturnTodoItemWhenDeleteExistingTodoItemWithId() throws Exception {
        when(todoItemRepo.findOne(anyLong())).thenReturn(todoItem1);
        doNothing().when(todoItemRepo).delete(Matchers.any(TodoItem.class));

        TodoItem todoItem = todoService.deleteTodoItem(1L);
        assertThat(todoItem.getId(), is(1L));
        assertThat(todoItem.getContent(), is("item1"));
        assertFalse(todoItem.getComplete());

        verify(todoItemRepo).findOne(anyLong());
        verify(todoItemRepo).delete(Matchers.any(TodoItem.class));
    }

    @Test
    public void shouldThrowTodoItemExceptionWhenDeleteNonExistItemInDb() throws Exception {
        when(todoItemRepo.findOne(anyLong())).thenReturn(null);

        expectedEx.expect(TodoNotFoundException.class);
        expectedEx.expectMessage("Todo item id 1 is not found");
        todoService.deleteTodoItem(1L);

        verify(todoItemRepo).findOne(anyLong());
    }
}
