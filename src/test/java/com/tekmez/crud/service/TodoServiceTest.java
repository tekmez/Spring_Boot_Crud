package com.tekmez.crud.service;

import com.tekmez.crud.model.dto.TodoDto;
import com.tekmez.crud.model.entity.TodoEntity;
import com.tekmez.crud.repository.TodoRepository;
import com.tekmez.crud.util.TodoMapper;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;
import java.util.Optional;

public class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTodoById_ShouldReturnDto_WhenExists() {
        TodoEntity entity = new TodoEntity();
        entity.setId(1L);
        entity.setTitle("Test todo service");
        entity.setCompleted(true);

        Mockito.when(todoRepository.findById(entity.getId())).thenReturn(Optional.of(entity));

        TodoDto result = todoService.getTodoById(entity.getId());

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(entity.getTitle());
        verify(todoRepository, times(1)).findById(entity.getId());
    }

    @Test
    void getTodoById_ShouldThrowException_WhenNotExists() {
        // given
        when(todoRepository.findById(999L)).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> todoService.getTodoById(999L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Todo not found");
    }

    @Test
    void createTodo_ShouldSaveAndReturnDto() {
        TodoDto dto = new TodoDto();
        dto.setTitle("Write Service Test");
        dto.setCompleted(false);

        TodoEntity savedEntity = TodoMapper.toEntity(dto);
        savedEntity.setId(1L);

        when(todoRepository.save(any(TodoEntity.class))).thenReturn(savedEntity);
        TodoDto result = todoService.createTodo(dto);


        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(savedEntity.getId());
        assertThat(result.getTitle()).isEqualTo(savedEntity.getTitle());
        verify(todoRepository, times(1)).save(any(TodoEntity.class));
    }
}
