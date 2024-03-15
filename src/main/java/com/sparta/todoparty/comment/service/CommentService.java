package com.sparta.todoparty.comment.service;

import com.sparta.todoparty.comment.dto.CommentRequestDto;
import com.sparta.todoparty.comment.dto.CommentResponseDto;
import com.sparta.todoparty.comment.entity.Comment;
import com.sparta.todoparty.todo.entity.TodoEntity;
import com.sparta.todoparty.user.entity.UserEntity;
import com.sparta.todoparty.exception.CommentNotFoundException;
import com.sparta.todoparty.comment.repository.CommentRepository;
import com.sparta.todoparty.todo.repository.TodoJpaRepository;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.RejectedExecutionException;

@Service
public class CommentService {

	private final CommentRepository commentRepository;
	private final TodoJpaRepository todoJpaRepository;
	private final MessageSource messageSource;

	public CommentService(CommentRepository commentRepository, TodoJpaRepository todoJpaRepository,
		MessageSource messageSource) {
		this.commentRepository = commentRepository;
		this.todoJpaRepository = todoJpaRepository;
		this.messageSource = messageSource;
	}

	public CommentResponseDto createComment(Long todoId, CommentRequestDto requestDto, UserEntity userEntity) {
		Comment comment = new Comment(todoId, requestDto, userEntity);
		commentRepository.save(comment);
		return new CommentResponseDto(comment);
	}

	public List<CommentResponseDto> getComments(Long todoId) {
		TodoEntity todoEntity = todoJpaRepository.findById(todoId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 할일카드 입니다."));
		List<Comment> comments = commentRepository.findByTodoIdOrderByCreateDate(todoEntity.getId());
		List<CommentResponseDto> responseDtoList = new ArrayList<>();
		for (Comment comment : comments) {
			responseDtoList.add(new CommentResponseDto(comment));
		}
		return responseDtoList;
	}

	public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto,
		UserEntity userEntity) {
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new CommentNotFoundException(messageSource.getMessage(
					"not.found.comment",
					null,
					"Not Found Comment",
					Locale.getDefault()
				))
			);
		if (!userEntity.getUsername().equals(comment.getUsername())) {
			throw new RejectedExecutionException("댓글의 작성자만 수정이 가능합니다.");
		}
		comment.update(requestDto);
		return new CommentResponseDto(comment);
	}

	public void deleteComment(Long commentId, UserEntity userEntity) {
		Comment comment = commentRepository.findById(commentId)
			.orElseThrow(() -> new CommentNotFoundException(messageSource.getMessage(
					"not.found.comment",
					null,
					"Not Found Comment",
					Locale.getDefault()
				))
			);
		if (!userEntity.getUsername().equals(comment.getUsername())) {
			throw new RejectedExecutionException("댓글의 작성자만 삭제가 가능합니다.");
		}
		commentRepository.delete(comment);
	}

	public void deleteCommentByTodoId(Long todoId) {
		commentRepository.deleteByTodoId(todoId);
	}
}
