package com.sparta.todoparty.entity;

import com.sparta.todoparty.dto.CommentRequestDto;
import com.sparta.todoparty.security.UserDetailsImpl;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comments")
public class Comment {
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

    public Comment(Long todoId, CommentRequestDto requestDto, UserDetailsImpl userDetails){
        this.todoId = todoId;
        this.content = requestDto.getContent();
        this.username = userDetails.getUsername();
        this.createDate = LocalDateTime.now();
    }
}
