package com.sparta.todoparty.comment.dto;

import com.sparta.todoparty.comment.entity.Comment;
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

    public CommentResponseDto(Comment comment){
        this.id = comment.getId();
        this.content = comment.getContent();
        this.username = comment.getUsername();
        this.createDate = comment.getCreateDate();
        this.todoId = comment.getTodoId();
    }
}
