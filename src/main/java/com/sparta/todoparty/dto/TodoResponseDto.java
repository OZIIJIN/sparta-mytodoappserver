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
    private String username;
    private LocalDateTime createDate;
    private  Boolean isCompleted;

    public TodoResponseDto(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.content = todo.getContent();
        this.username = todo.getUsername();
        this.createDate = todo.getCreateDate();
        this.isCompleted = todo.getIscompleted();
    }
}
