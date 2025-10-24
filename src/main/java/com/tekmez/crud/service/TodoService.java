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
    public TodoEntity createTodo(TodoEntity TodoEntity) {
        return todoRepository.save(TodoEntity);
    }

    @Override
    public TodoEntity updateTodoById(Long id, TodoEntity updateTodo) {
        TodoEntity todo = todoRepository.getReferenceById(id);
        todo.setTitle(updateTodo.getTitle());
        todo.setCompleted(updateTodo.isCompleted());
        return todoRepository.save(todo);
    }

    @Override
    public void deleteTodoById(Long id) {
        todoRepository.deleteById(id);
    }
}
