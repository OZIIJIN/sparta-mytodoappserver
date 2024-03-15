package com.sparta.todoparty.comment.dto;

import com.sparta.todoparty.comment.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CommentResponseDto {
    private Long id;

    private String content;

    private String username;

    private LocalDateTime createDate;

    private Long todoId;

    public CommentResponseDto(CommentEntity commentEntity){
        this.id = commentEntity.getId();
        this.content = commentEntity.getContent();
        this.username = commentEntity.getUsername();
        this.createDate = commentEntity.getCreateDate();
        this.todoId = commentEntity.getTodoId();
    }
}
