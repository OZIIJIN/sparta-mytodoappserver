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
import java.util.Optional;
import java.util.concurrent.RejectedExecutionException;

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

    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto, UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
        if(!userDetails.getUsername().equals(comment.getUsername())){
            throw new RejectedExecutionException("댓글의 작성자만 수정이 가능합니다.");
        }
        comment.update(requestDto);
        return new CommentResponseDto(comment);
    }

    public void deleteComment(Long commentId, UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
        if(!userDetails.getUsername().equals(comment.getUsername())){
            throw new RejectedExecutionException("댓글의 작성자만 삭제가 가능합니다.");
        }
        commentRepository.delete(comment);
    }

    public void deleteCommentByTodoId(Long todoId) {
        commentRepository.deleteByTodoId(todoId);
    }
}
