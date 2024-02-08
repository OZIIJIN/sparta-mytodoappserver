package com.sparta.todoparty.dto;

import com.sparta.todoparty.entity.Todo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoResponseDto {
    private Long id;
    private String title;
    private String content;

    public TodoResponseDto(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.content = todo.getContent();
    }
}
