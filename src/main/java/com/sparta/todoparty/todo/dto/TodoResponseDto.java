package com.sparta.todoparty.todo.dto;

import com.sparta.todoparty.todo.domain.TodoDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class TodoResponseDto {

    private Long todoId;

    private String title;

    private String content;

    private LocalDateTime createAt;

    private LocalDateTime modifiedAt;

    private Long createdBy;

    private Boolean isCompleted;

    public TodoResponseDto(TodoDomain todoDomain) {
        this.todoId = todoDomain.getTodoId();
        this.title = todoDomain.getTitle();
        this.content = todoDomain.getContent();
        this.createAt = todoDomain.getCreateAt();
        this.modifiedAt = todoDomain.getModifiedAt();
        this.createdBy = todoDomain.getCreatedBy();
        this.isCompleted = todoDomain.getIsCompleted();
    }
}
