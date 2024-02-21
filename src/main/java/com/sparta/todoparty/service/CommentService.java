package com.sparta.todoparty.service;

import com.sparta.todoparty.dto.CommentRequestDto;
import com.sparta.todoparty.dto.CommentResponseDto;
import com.sparta.todoparty.entity.Comment;
import com.sparta.todoparty.entity.Todo;
import com.sparta.todoparty.repository.CommentRepository;
import com.sparta.todoparty.repository.TodoRepository;
import com.sparta.todoparty.security.UserDetailsImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final TodoRepository todoRepository;

    public CommentService(CommentRepository commentRepository, TodoRepository todoRepository) {
        this.commentRepository = commentRepository;
        this.todoRepository = todoRepository;
    }

    public CommentResponseDto createComment(Long todoId, CommentRequestDto requestDto, UserDetailsImpl userDetails) {
        Comment comment = new Comment(todoId, requestDto, userDetails);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    public List<CommentResponseDto> getComments(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 할일카드 입니다."));
        List<Comment> comments = commentRepository.findByTodoIdOrderByCreateDate(todo.getId());
        List<CommentResponseDto> responseDtoList = new ArrayList<>();
        for(Comment comment : comments){
            responseDtoList.add(new CommentResponseDto(comment));
        }
        return responseDtoList;
    }
}
