package com.tekmez.crud.model.dto;

import jakarta.validation.constraints.NotBlank;

public class TodoDto {

    @NotBlank(message = "Title can not be empty")
    private String title;
    private boolean completed;
    private Long id;

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }


    public void setCompleted(boolean completed) {
        this.completed = completed;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
