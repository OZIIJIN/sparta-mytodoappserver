package com.sparta.todoparty.todo.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class MyTodoListResponseDto {
    private List<TodoResponseDto> todoList;

    public MyTodoListResponseDto(List<TodoResponseDto> todoList){
        this.todoList = todoList;
    }
}
