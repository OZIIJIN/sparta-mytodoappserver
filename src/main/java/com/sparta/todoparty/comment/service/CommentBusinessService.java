package com.sparta.todoparty.comment.service;

import com.sparta.todoparty.comment.domain.CommentDomain;
import com.sparta.todoparty.comment.dto.CommentRequestDto;
import com.sparta.todoparty.comment.dto.CommentResponseDto;
import com.sparta.todoparty.comment.entity.CommentEntity;
import com.sparta.todoparty.todo.domain.TodoDomain;
import com.sparta.todoparty.todo.dto.TodoResponseDto;
import com.sparta.todoparty.todo.entity.TodoEntity;
import com.sparta.todoparty.todo.service.TodoDomainService;
import com.sparta.todoparty.user.domain.UserDomain;
import com.sparta.todoparty.user.entity.UserEntity;
import com.sparta.todoparty.exception.CommentNotFoundException;
import com.sparta.todoparty.comment.repository.CommentJpaRepository;
import com.sparta.todoparty.todo.repository.TodoJpaRepository;
import com.sparta.todoparty.user.service.UserDomainService;
import java.util.SimpleTimeZone;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class CommentBusinessService {

	private final CommentDomainService commentDomainService;
	private final UserDomainService userDomainService;


	public CommentResponseDto createComment(Long todoId, String content, UserEntity userEntity) {
		UserDomain userDomain = userDomainService.getUser(userEntity.getUsername());
		CommentEntity commentEntity = new CommentEntity(todoId, content, userDomain.getUserId());
		CommentResponseDto commentResponseDto = new CommentResponseDto(
			commentDomainService.save(commentEntity));

		return commentResponseDto;
	}

	public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto,
		UserEntity userEntity) {
		UserDomain userDomain = userDomainService.getUser(userEntity.getUsername());
		String newContent = requestDto.getContent();
		CommentDomain commentDomain = commentDomainService.updateComment(commentId, newContent,
			userDomain.getUserId());
		return new CommentResponseDto(commentDomain);
	}

	public void deleteComment(Long commentId, UserEntity userEntity) {
		UserDomain userDomain = userDomainService.getUser(userEntity.getUsername());
		commentDomainService.deleteComment(commentId, userDomain.getUserId());
	}

}
