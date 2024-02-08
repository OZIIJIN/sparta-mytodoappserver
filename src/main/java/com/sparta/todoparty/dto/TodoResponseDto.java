package com.sparta.todoparty.dto;

import com.sparta.todoparty.entity.Todo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TodoResponseDto extends CommonResponseDto{
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createDate;

    public TodoResponseDto(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.content = todo.getContent();
        this.createDate = todo.getCreateDate();
    }
}
