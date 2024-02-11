package com.sparta.todoparty.entity;

import com.sparta.todoparty.dto.TodoRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@Getter
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
    private LocalDateTime createDate;

    @Column
    private Boolean iscompleted;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Todo(TodoRequestDto todoRequestDto){
        this.title = todoRequestDto.getTitle();
        this.content = todoRequestDto.getContent();
        //생성할 때 정해짐
        this.createDate = LocalDateTime.now();
        this.iscompleted = false;
    }

    //연관관계 맵핑하는 메서드
    public void setUser(User user){
        this.user = user;
    }

    //수정 관련 메서드
    public void setTitle(String title){
        this.title = title;
    }
    public void setContent(String content){
        this.content = content;
    }

    public void complete(){
        this.iscompleted = true;
    }
}
