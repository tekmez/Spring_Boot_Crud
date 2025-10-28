package com.tekmez.crud.util;

import com.tekmez.crud.model.entity.TodoEntity;
import com.tekmez.crud.model.dto.TodoDto;

public class TodoMapper {

    public static TodoDto toDto (TodoEntity entity){
        TodoDto dto = new TodoDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setCompleted(entity.isCompleted());
        return dto;
    }

    public static TodoEntity toEntity (TodoDto dto){
        TodoEntity entity = new TodoEntity();
        entity.setTitle(dto.getTitle());
        entity.setCompleted(dto.isCompleted());
        return entity;
    }

}
