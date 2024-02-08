package com.sparta.todoparty.dto;

import com.sparta.todoparty.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TodoListResponseDto {
    private UserDto user;
    private List<TodoResponseDto> todoList;

    public TodoListResponseDto (UserDto user, List<TodoResponseDto> todoList){
        this.user = user;
        this.todoList = todoList;
    }
}
