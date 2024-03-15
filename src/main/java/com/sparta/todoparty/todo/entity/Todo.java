package com.sparta.todoparty.todo.entity;

import com.sparta.todoparty.todo.dto.TodoRequestDto;
import com.sparta.todoparty.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "todos")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String username;

    @Column
    private LocalDateTime createDate;

    @Column
    private Boolean iscompleted;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Todo(TodoRequestDto todoRequestDto, User user){
        this.title = todoRequestDto.getTitle();
        this.content = todoRequestDto.getContent();
        this.username = user.getUsername();
        this.user = user;
        //생성할 때 정해짐
        this.createDate = LocalDateTime.now();
        this.iscompleted = false;
    }

    //수정 관련 메서드
    public void update(TodoRequestDto todoRequestDto){
        this.title = todoRequestDto.getTitle();
        this.content = todoRequestDto.getContent();
    }

    public void complete(){
        this.iscompleted = true;
    }
}
