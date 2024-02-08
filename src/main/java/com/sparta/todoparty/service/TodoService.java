package com.sparta.todoparty.service;

import com.sparta.todoparty.dto.TodoRequestDto;
import com.sparta.todoparty.dto.TodoResponseDto;
import com.sparta.todoparty.entity.Todo;
import com.sparta.todoparty.entity.User;
import com.sparta.todoparty.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoResponseDto postTodo(TodoRequestDto todoRequestDto, User user){
        Todo todo = new Todo(todoRequestDto);
        todo.setUser(user);
        todoRepository.save(todo);
        return new TodoResponseDto(todo);
    }

    public TodoResponseDto getTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 할일 id 입니다."));

        return new  TodoResponseDto(todo);
    }
}
