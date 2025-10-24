package com.tekmez.crud.service;

import com.tekmez.crud.interfaces.ITodoService;
import com.tekmez.crud.model.TodoEntity;
import com.tekmez.crud.repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService implements ITodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository){
        this.todoRepository = todoRepository;
    }

    @Override
    public List<TodoEntity> getAllTodos() {
        return todoRepository.findAll();
    }

    @Override
    public TodoEntity getTodoById(Long id) {
        return todoRepository.getReferenceById(id);
    }

    @Override
    public TodoEntity createTodo(TodoEntity todoEntity) {
        return todoRepository.save(todoEntity);
    }

    @Override
    public TodoEntity updateTodoById(Long id, TodoEntity todoEntity) {
        TodoEntity todo = todoRepository.getReferenceById(id);
        todo.setTitle(todoEntity.getTitle());
        todo.setCompleted(todoEntity.isCompleted());
        return todoRepository.save(todo);
    }

    @Override
    public void deleteTodoById(Long id) {
        todoRepository.deleteById(id);
    }
}
