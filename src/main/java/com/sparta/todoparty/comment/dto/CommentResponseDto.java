package com.sparta.todoparty.comment.dto;

import com.sparta.todoparty.comment.domain.CommentDomain;
import com.sparta.todoparty.comment.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CommentResponseDto {
    private Long commentId;

    private String content;

    private Long createdBy;

    private Long registeredAt;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public CommentResponseDto(CommentDomain commentDomain){
        this.commentId = commentDomain.getCommentId();
        this.content = commentDomain.getContent();
        this.createdBy = commentDomain.getCreatedBy();
        this.registeredAt = commentDomain.getRegisteredAt();
        this.createdAt = commentDomain.getCreatedAt();
        this.modifiedAt = commentDomain.getModifiedAt();
    }
}
