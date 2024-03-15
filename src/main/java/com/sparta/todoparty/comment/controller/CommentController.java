package com.sparta.todoparty.comment.controller;

import com.sparta.todoparty.comment.dto.CommentRequestDto;
import com.sparta.todoparty.comment.dto.CommentResponseDto;
import com.sparta.todoparty.security.UserDetailsImpl;
import com.sparta.todoparty.comment.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
	public CommentResponseDto createComment(@PathVariable Long todoId,
		@RequestBody @Valid CommentRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {

		return commentService.createComment(todoId, requestDto, userDetails.getUserEntity());
	}

	//할일카드의 전체 댓글 조회 기능
	@GetMapping("/{todoId}")
	public List<CommentResponseDto> getComments(@PathVariable Long todoId) {
		return commentService.getComments(todoId);
	}

	//댓글 수정 기능
	@Transactional
	@PutMapping("/{commentId}")
	public CommentResponseDto updateComment(@PathVariable Long commentId,
		@RequestBody @Valid CommentRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return commentService.updateComment(commentId, requestDto, userDetails.getUserEntity());
	}

	//댓글 삭제 기능
	@Transactional
	@DeleteMapping("/{commentId}")
	public void deleteComment(@PathVariable Long commentId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		commentService.deleteComment(commentId, userDetails.getUserEntity());
	}

}
