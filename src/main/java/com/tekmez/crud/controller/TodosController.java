package com.tekmez.crud.controller;

import com.tekmez.crud.dto.TodoRequestDto;
import com.tekmez.crud.dto.TodoResponseDto;
import com.tekmez.crud.interfaces.ITodoService;
import com.tekmez.crud.model.TodoEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/todos")
public class TodosController {

    private final ITodoService todoService;

    public TodosController(ITodoService todoService){
        this.todoService = todoService;
    }

    @GetMapping
    public List<TodoResponseDto> getAllTodo(){
        return todoService.getAllTodos().stream()
                .map(TodoResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public TodoResponseDto getTodoById(@PathVariable Long id){
        TodoEntity todo =  todoService.getTodoById(id);
        return TodoResponseDto.fromEntity(todo);
    }

    @PostMapping("/create")
    public TodoResponseDto createTodo(@RequestBody TodoRequestDto todo){
        TodoEntity entity = todo.toEntity();
        TodoEntity saved = todoService.createTodo(entity);
        return TodoResponseDto.fromEntity(saved);
    }

    @PutMapping("/update")
    public TodoResponseDto updateTodo(@RequestParam Long id, @RequestBody TodoRequestDto updateTodo){
        TodoEntity updateEntity = updateTodo.toEntity();
        TodoEntity saved = todoService.updateTodoById(id, updateEntity);
        return TodoResponseDto.fromEntity(saved);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTodo(@PathVariable Long id){
        todoService.deleteTodoById(id);
    }

}
