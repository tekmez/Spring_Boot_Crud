package com.tekmez.crud.controller;

import com.tekmez.crud.model.dto.TodoDto;
import com.tekmez.crud.interfaces.ITodoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodosController {

    private final ITodoService todoService;

    public TodosController(ITodoService todoService){
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<List<TodoDto>> getAllTodo(){
        List<TodoDto> todos = todoService.getAllTodos();
        return ResponseEntity.ok(todos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TodoDto> getTodoById(@PathVariable Long id){
        TodoDto todo = todoService.getTodoById(id);
        return ResponseEntity.ok(todo);
    }

    @PostMapping("/create")
    public ResponseEntity<TodoDto> createTodo(@Valid @RequestBody TodoDto todo){
        TodoDto created = todoService.createTodo(todo);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/update")
    public ResponseEntity<TodoDto> updateTodo(@RequestParam Long id, @Valid @RequestBody TodoDto updateTodo){
        TodoDto updated =  todoService.updateTodoById(id, updateTodo);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id){
        todoService.deleteTodoById(id);
        return ResponseEntity.noContent().build();
    }

}
