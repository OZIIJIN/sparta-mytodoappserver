package com.sparta.todoparty.todo.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class TodoListResponseDto {
    private List<TodoResponseDto> todoList;

    public TodoListResponseDto(List<TodoResponseDto> todoList){
        this.todoList = todoList;
    }
}
