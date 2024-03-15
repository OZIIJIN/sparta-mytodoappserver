package com.sparta.todoparty.comment.entity;

import com.sparta.todoparty.comment.dto.CommentRequestDto;
import com.sparta.todoparty.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "tb_comment")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String content;

    @Column
    private String username;

    @Column
    private LocalDateTime createDate;

    @Column
    private Long todoId;

    public CommentEntity(Long todoId, CommentRequestDto requestDto, UserEntity userEntity){
        this.todoId = todoId;
        this.content = requestDto.getContent();
        this.username = userEntity.getUsername();
        this.createDate = LocalDateTime.now();
    }

    public void setContent(String s){
        this.content = s;
    }
    public void update(CommentRequestDto requestDto) {
        this.content = requestDto.getContent();
    }
}
