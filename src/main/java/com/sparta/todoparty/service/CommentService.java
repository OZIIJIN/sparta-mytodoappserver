package com.sparta.todoparty.service;

import com.sparta.todoparty.dto.CommentRequestDto;
import com.sparta.todoparty.dto.CommentResponseDto;
import com.sparta.todoparty.entity.Comment;
import com.sparta.todoparty.repository.CommentRepository;
import com.sparta.todoparty.security.UserDetailsImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public CommentResponseDto createComment(Long todoId, CommentRequestDto requestDto, UserDetailsImpl userDetails) {
        Comment comment = new Comment(todoId, requestDto, userDetails);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    public List<CommentResponseDto> getComments(Long todoId) {
        List<Comment> comments = commentRepository.findByTodoIdOrderByCreateDate(todoId);
        List<CommentResponseDto> responseDtoList = new ArrayList<>();
        for(Comment comment : comments){
            responseDtoList.add(new CommentResponseDto(comment));
        }
        return responseDtoList;
    }
}
