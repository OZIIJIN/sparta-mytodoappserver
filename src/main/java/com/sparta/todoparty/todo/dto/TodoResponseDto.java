package com.sparta.todoparty.todo.dto;

import com.sparta.todoparty.comment.domain.CommentDomain;
import com.sparta.todoparty.comment.dto.CommentRequestDto;
import com.sparta.todoparty.comment.dto.CommentResponseDto;
import com.sparta.todoparty.todo.domain.TodoDomain;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TodoResponseDto {

    private Long todoId;

    private String title;

    private String content;

    private LocalDateTime createAt;

    private LocalDateTime modifiedAt;

    private Long createdBy;

    private Boolean isCompleted;


    @Builder
    public TodoResponseDto(TodoDomain todoDomain) {
        this.todoId = todoDomain.getTodoId();
        this.title = todoDomain.getTitle();
        this.content = todoDomain.getContent();
        this.createAt = todoDomain.getCreateAt();
        this.modifiedAt = todoDomain.getModifiedAt();
        this.createdBy = todoDomain.getCreatedBy();
        this.isCompleted = todoDomain.getIsCompleted();}


}
