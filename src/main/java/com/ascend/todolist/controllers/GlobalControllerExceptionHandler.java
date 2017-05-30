package com.ascend.todolist.controllers;

import com.ascend.todolist.exceptions.TodoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by BiG on 5/28/2017 AD.
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler {
    @ExceptionHandler
    public void handleTodoException(TodoNotFoundException exception, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }
}
