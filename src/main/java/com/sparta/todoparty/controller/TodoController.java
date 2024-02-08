package com.sparta.todoparty.controller;

import com.sparta.todoparty.dto.CommonResponseDto;
import com.sparta.todoparty.dto.TodoRequestDto;
import com.sparta.todoparty.dto.TodoResponseDto;
import com.sparta.todoparty.dto.UserRequsetDto;
import com.sparta.todoparty.security.UserDetailsImpl;
import com.sparta.todoparty.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public ResponseEntity<TodoResponseDto> postTodo(@RequestBody TodoRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        //UserDetailsImpl에 Getter 추가
        TodoResponseDto todoResponseDto = todoService.postTodo(requestDto, userDetails.getUser());
        return ResponseEntity.status(201).body(todoResponseDto);
    }

    @GetMapping("/{todoId}")
    public ResponseEntity<CommonResponseDto> getTodo(@PathVariable Long todoId){
        try {
            TodoResponseDto responseDto = todoService.getTodo(todoId);
            return ResponseEntity.ok().body(responseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
    }

    @GetMapping
    public ResponseEntity<Void> getTodoList(){
        return ResponseEntity.ok().build();
    }
}
