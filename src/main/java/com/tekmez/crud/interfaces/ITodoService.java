package com.tekmez.crud.interfaces;

import com.tekmez.crud.model.dto.TodoDto;
import java.util.List;

public interface ITodoService {
    List<TodoDto> getAllTodos();
    TodoDto getTodoById(Long id);
    TodoDto createTodo(TodoDto TodoDto);
    TodoDto updateTodoById(Long id, TodoDto TodoDto);
    boolean deleteTodoById(Long id);
}
