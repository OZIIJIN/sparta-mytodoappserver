package com.sparta.todoparty.comment.controller;

import com.sparta.todoparty.comment.dto.CommentRequestDto;
import com.sparta.todoparty.comment.dto.CommentResponseDto;
import com.sparta.todoparty.common.ResponseDto;
import com.sparta.todoparty.security.UserDetailsImpl;
import com.sparta.todoparty.comment.service.CommentBusinessService;
import com.sparta.todoparty.todo.dto.TodoResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

	private final CommentBusinessService commentBusinessService;

	//댓글 작성 기능
	@Transactional
	@PostMapping("/todos/{todoId}/comments")
	public ResponseEntity<ResponseDto<CommentResponseDto>> createComment(@PathVariable Long todoId,
		@RequestBody @Valid CommentRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {

		String content = requestDto.getContent();
		CommentResponseDto commentResponseDto = commentBusinessService.createComment(todoId,
			content, userDetails.getUserEntity());

		return ResponseEntity.ok()
			.body(ResponseDto.<CommentResponseDto>builder()
				.message("댓글 작성 성공")
				.data(commentResponseDto)
				.build());
	}

	//댓글 수정 기능
	@Transactional
	@PutMapping("/comments/{commentId}")
	public CommentResponseDto updateComment(@PathVariable Long commentId,
		@RequestBody @Valid CommentRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return commentBusinessService.updateComment(commentId, requestDto,
			userDetails.getUserEntity());
	}

	//댓글 삭제 기능
	@Transactional
	@DeleteMapping("/{commentId}")
	public void deleteComment(@PathVariable Long commentId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		commentBusinessService.deleteComment(commentId, userDetails.getUserEntity());
	}

}
