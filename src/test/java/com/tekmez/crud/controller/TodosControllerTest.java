package com.tekmez.crud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tekmez.crud.interfaces.ITodoService;
import com.tekmez.crud.model.dto.TodoDto;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(TodosController.class)
public class TodosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ITodoService todoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getTodoById_ShouldReturnNotFound_WhenTodoDoesNotExist() throws Exception{
        Long invalidId = 999L;
        Mockito.when(todoService.getTodoById(invalidId))
                .thenThrow(new EntityNotFoundException("Todo Not Found"));

        mockMvc.perform(get("/todos/{id}", invalidId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getTodoById_ShouldReturnTodo_WhenExist() throws Exception{
        TodoDto mockTodo = new TodoDto();
        mockTodo.setId(1L);
        mockTodo.setTitle("Test Todo");
        mockTodo.setCompleted(true);

        Mockito.when(todoService.getTodoById(1L)).thenReturn(mockTodo);

        mockMvc.perform(get("/todos/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Todo"))
                .andExpect(jsonPath("$.completed").value(true));

    }


    @Test
    void createTodo_ShouldReturnCreated_WhenValid() throws Exception{
        TodoDto mockReq = new TodoDto();
        mockReq.setTitle("Learn Spring");
        mockReq.setCompleted(false);

        TodoDto created = new TodoDto();
        created.setId(1L);
        created.setTitle(mockReq.getTitle());
        created.setCompleted(mockReq.isCompleted());

        Mockito.when(todoService.createTodo(Mockito.any())).thenReturn(created);


        mockMvc.perform(post("/todos/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mockReq)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Learn Spring"))
                .andExpect(jsonPath("$.completed").value(false));

    }


    @Test
    void createTodo_ShouldReturnBadRequest_WhenInvalid() throws Exception {
        TodoDto invalid = new TodoDto();
        invalid.setTitle("  ");
        invalid.setCompleted(true);

        Mockito.when(todoService.createTodo(Mockito.any()))
                .thenThrow(new IllegalStateException("Should not be called"));

        mockMvc.perform(
                        post("/todos/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(invalid))
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    void deleteTodo_ShouldReturnNoContent_WhenExists() throws Exception {
        Long existingId = 1L;
        Mockito.when(todoService.deleteTodoById(existingId)).thenReturn(true);

        mockMvc.perform(delete("/todos/delete/{id}", existingId))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteTodo_ShouldReturnNotFound_WhenTodoDoesNotExist() throws Exception {
        Long invalidId = 999L;
        Mockito.when(todoService.deleteTodoById(invalidId))
                .thenThrow(new jakarta.persistence.EntityNotFoundException("Todo not found"));

        mockMvc.perform(delete("/todos/delete/{id}", invalidId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
