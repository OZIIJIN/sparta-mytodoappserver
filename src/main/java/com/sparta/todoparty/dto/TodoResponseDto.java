package com.sparta.todoparty.dto;

import com.sparta.todoparty.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class TodoResponseDto extends CommonResponseDto{
    private Long id;
    private String title;
    private String content;
    private UserDto user;
    private LocalDateTime createDate;

    public TodoResponseDto(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.content = todo.getContent();
        this.user = new UserDto(todo.getUser());
        this.createDate = todo.getCreateDate();
    }
}
