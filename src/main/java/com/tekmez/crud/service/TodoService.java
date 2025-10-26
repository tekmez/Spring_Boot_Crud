package com.tekmez.crud.service;

import com.tekmez.crud.interfaces.ITodoService;
import com.tekmez.crud.model.dto.TodoDto;
import com.tekmez.crud.model.entity.TodoEntity;
import com.tekmez.crud.repository.TodoRepository;
import com.tekmez.crud.util.TodoMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService implements ITodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository){
        this.todoRepository = todoRepository;
    }

    @Override
    public List<TodoDto> getAllTodos() {
        return todoRepository.findAll()
                .stream()
                .map(TodoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TodoDto getTodoById(Long id) {
        return  todoRepository.findById(id)
                .map(TodoMapper::toDto)
                .orElse(null);
    }

    @Override
    public TodoDto createTodo(TodoDto todoDto) {
        TodoEntity todo = new TodoEntity();
        todo.setTitle(todoDto.getTitle());
        todo.setCompleted(todoDto.isCompleted());
        todoRepository.save(todo);
        return TodoMapper.toDto(todo);
    }

    @Override
    public TodoDto updateTodoById(Long id, TodoDto todoDto) {
        TodoEntity todo = todoRepository.getReferenceById(id);
        todo.setTitle(todoDto.getTitle());
        todo.setCompleted(todoDto.isCompleted());
        TodoEntity savedEntity =  todoRepository.save(todo);
        return TodoMapper.toDto(savedEntity);
    }

    @Override
    public boolean deleteTodoById(Long id) {
        todoRepository.deleteById(id);
        return false;
    }
}
