package com.sparta.todoparty.controller;

import com.sparta.todoparty.dto.CommentRequestDto;
import com.sparta.todoparty.dto.CommentResponseDto;
import com.sparta.todoparty.security.UserDetailsImpl;
import com.sparta.todoparty.service.CommentService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    //댓글 작성 기능
    @Transactional
    @PostMapping("/todo/{todoId}/create")
    public CommentResponseDto createComment(@PathVariable Long todoId, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){

        return commentService.createComment(todoId, requestDto, userDetails);
    }

    //할일카드의 전체 댓글 조회 기능
    @GetMapping("/{todoId}")
    public List<CommentResponseDto> getComments(@PathVariable Long todoId){
        return commentService.getComments(todoId);
    }

    
}
