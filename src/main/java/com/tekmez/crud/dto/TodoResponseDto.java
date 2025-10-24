package com.tekmez.crud.dto;

import com.tekmez.crud.model.TodoEntity;

public class TodoResponseDto {
    private String title;
    private boolean completed;
    private Long id;

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public Long getId() {
        return id;
    }

    public static TodoResponseDto fromEntity(TodoEntity todoEntity){
        TodoResponseDto dto = new TodoResponseDto();
        dto.id = todoEntity.getId();
        dto.completed = todoEntity.isCompleted();
        dto.title = todoEntity.getTitle();
        return dto;
    }

}
