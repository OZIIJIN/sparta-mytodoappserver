package com.sparta.todoparty.todo.entity;

import com.sparta.todoparty.common.TimeStamp;
import com.sparta.todoparty.todo.dto.TodoRequestDto;
import com.sparta.todoparty.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "tb_todo")
public class TodoEntity extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private Boolean iscompleted;

    @Column
    private Long createdBy;

    public TodoEntity(TodoRequestDto todoRequestDto, Long userId) {
        this.title = todoRequestDto.getTitle();
        this.content = todoRequestDto.getContent();
        this.createdBy = userId;
        this.iscompleted = false;
    }

    //수정 관련 메서드
    public void update(String newTitle, String newContent){
        this.title = newTitle;
        this.content = newContent;
    }

    public void complete(){
        this.iscompleted = true;
    }
}
