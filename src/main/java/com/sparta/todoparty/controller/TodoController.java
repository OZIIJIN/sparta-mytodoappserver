package com.sparta.todoparty.controller;

import com.sparta.todoparty.dto.*;
import com.sparta.todoparty.entity.Todo;
import com.sparta.todoparty.entity.User;
import com.sparta.todoparty.security.UserDetailsImpl;
import com.sparta.todoparty.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;


    @PostMapping
    public ResponseEntity<TodoResponseDto> postTodo(@RequestBody TodoRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        //UserDetailsImpl에 Getter 추가
        TodoResponseDto todoResponseDto = todoService.postTodo(requestDto, userDetails.getUser());
        return ResponseEntity.status(201).body(todoResponseDto);
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<CommonResponseDto> getTodo(@PathVariable Long todoId){
        try {
            TodoResponseDto responseDTO = todoService.getTodo(todoId);
            return ResponseEntity.ok().body(responseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @GetMapping
    public ResponseEntity<List<TodoListResponseDto>> getTodoList() {
        List<TodoListResponseDto> responseDtoList = new ArrayList<>();

        Map<UserDto, List<TodoResponseDto>> responseDTOMap = todoService.getUserTodoMap();

        responseDTOMap.forEach((key, value) -> responseDtoList.add(new TodoListResponseDto(key, value)));

        return ResponseEntity.ok().body(responseDtoList);
    }

    @PutMapping("/{todoId}")
    public ResponseEntity<CommonResponseDto> putTodo(@PathVariable Long todoId, @RequestBody TodoRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        //UserDetailsImpl에 Getter 추가
        TodoResponseDto todoResponseDto = null;
        try {
            todoResponseDto = todoService.updateTodo(todoId, requestDto, userDetails.getUser());
            return ResponseEntity.ok().body(todoResponseDto);
        } catch (RejectedExecutionException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }
}
