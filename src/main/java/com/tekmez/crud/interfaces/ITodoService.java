package com.tekmez.crud.interfaces;

import com.tekmez.crud.model.TodoEntity;

import java.util.List;

public interface ITodoService {
    List<TodoEntity> getAllTodos();
    TodoEntity getTodoById(Long id);
    TodoEntity createTodo(TodoEntity todoEnitiy);
    TodoEntity updateTodoById(Long id, TodoEntity todoEnitiy);
    void deleteTodoById(Long id);
}
