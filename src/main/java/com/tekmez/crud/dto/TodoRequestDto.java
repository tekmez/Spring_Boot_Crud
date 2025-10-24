package com.tekmez.crud.dto;

import com.tekmez.crud.model.TodoEntity;

public class TodoRequestDto {
    private String title;
    private boolean completed;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    public TodoEntity toEntity() {
        TodoEntity entity = new TodoEntity();
        entity.setTitle(this.title);
        entity.setCompleted(this.completed);
        return entity;
    }
}
