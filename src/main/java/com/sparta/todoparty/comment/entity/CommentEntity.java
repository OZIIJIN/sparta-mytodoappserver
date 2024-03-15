package com.sparta.todoparty.comment.entity;

import com.sparta.todoparty.comment.dto.CommentRequestDto;
import com.sparta.todoparty.common.TimeStamp;
import com.sparta.todoparty.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "tb_comment")
public class CommentEntity extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @Column
    private Long createdBy;

    @Column
    private Long registeredAt;

    public CommentEntity(Long todoId, String content, Long userId){
        this.registeredAt = todoId;
        this.content = content;
        this.createdBy = userId;
    }

    public void update(String newContent) {
        this.content = newContent;
    }
}
