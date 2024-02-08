package com.sparta.todoparty.controller;

import com.sparta.todoparty.dto.CommonResponseDto;
import com.sparta.todoparty.dto.UserRequsetDto;
import com.sparta.todoparty.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto> signup(@Valid @RequestBody UserRequsetDto requsetDto){
        //IllegalArgumentException이 왔을 때 잡아줘야함
        try {
            userService.signup(requsetDto);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body(new CommonResponseDto("중복된 username 입니다.", HttpStatus.BAD_REQUEST.value()));
        }

        return ResponseEntity.status(201).body(new CommonResponseDto("회원가입 성공", 201));
        //여기서 201 -> HttpStatus.CREATED.value() 할 수 있음
    }
}
