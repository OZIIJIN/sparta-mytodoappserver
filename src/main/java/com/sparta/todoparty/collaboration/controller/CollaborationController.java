package com.sparta.todoparty.collaboration.controller;

import com.sparta.todoparty.collaboration.dto.CollaborationRequestDto;
import com.sparta.todoparty.collaboration.dto.CollaborationResponseDto;
import com.sparta.todoparty.collaboration.service.CollaborationBusinessService;
import com.sparta.todoparty.comment.dto.CommentRequestDto;
import com.sparta.todoparty.comment.dto.CommentResponseDto;
import com.sparta.todoparty.common.ResponseDto;
import com.sparta.todoparty.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/collaborations")
public class CollaborationController {

	private final CollaborationBusinessService collaborationBusinessService;

	@PostMapping("/users/{userId}")
	public ResponseEntity<ResponseDto<CollaborationResponseDto>> createCollaboration(
		@PathVariable Long userId, @RequestBody @Valid CollaborationRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {

		String todo = requestDto.getTodo();

		CollaborationResponseDto collaborationResponseDto = collaborationBusinessService.createCollaboration(
			todo, userId, userDetails.getUserEntity());

		return ResponseEntity.ok()
			.body(ResponseDto.<CollaborationResponseDto>builder()
				.message("콜라보 생성 성공")
				.data(collaborationResponseDto)
				.build());
	}

	@Transactional
	@PatchMapping("/{collaborationID}/acceptance")
	public ResponseEntity<ResponseDto<CollaborationResponseDto>> acceptCollaboration(
		@PathVariable Long collaborationID) {

		CollaborationResponseDto collaborationResponseDto = collaborationBusinessService.acceptCollaboration(
			collaborationID);

		return ResponseEntity.ok()
			.body(ResponseDto.<CollaborationResponseDto>builder()
				.message("협동 일정 수락")
				.data(collaborationResponseDto)
				.build());

	}

	@Transactional
	@PatchMapping("/{collaborationID}/complete")
	public ResponseEntity<ResponseDto<CollaborationResponseDto>> completeCollaboration(
		@PathVariable Long collaborationID) {

		CollaborationResponseDto collaborationResponseDto = collaborationBusinessService.completeCollaboration(
			collaborationID);

		return ResponseEntity.ok()
			.body(ResponseDto.<CollaborationResponseDto>builder()
				.message("협동 일정 완료")
				.data(collaborationResponseDto)
				.build());

	}
}
