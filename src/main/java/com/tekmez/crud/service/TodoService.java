package com.tekmez.crud.service;

import com.tekmez.crud.interfaces.ITodoService;
import com.tekmez.crud.model.dto.TodoDto;
import com.tekmez.crud.model.entity.TodoEntity;
import com.tekmez.crud.repository.TodoRepository;
import com.tekmez.crud.util.TodoMapper;
import jakarta.persistence.EntityNotFoundException;
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
        TodoEntity entity = todoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Todo not found with id: " + id));
        return TodoMapper.toDto(entity);
    }

    @Override
    public TodoDto createTodo(TodoDto todoDto) {
        TodoEntity entity = TodoMapper.toEntity(todoDto);
        TodoEntity saved = todoRepository.save(entity);
        return TodoMapper.toDto(saved);
    }

    @Override
    public TodoDto updateTodoById(Long id, TodoDto todoDto) {
        TodoEntity entity = todoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Todo not found with id: " + id));
        entity.setTitle(todoDto.getTitle());
        entity.setCompleted(todoDto.isCompleted());
        TodoEntity updated  =  todoRepository.save(entity);
        return TodoMapper.toDto(updated);
    }

    @Override
    public boolean deleteTodoById(Long id) {
        TodoEntity entity = todoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Todo not found with id: " + id));
        todoRepository.delete(entity);
        return true;
    }
}
