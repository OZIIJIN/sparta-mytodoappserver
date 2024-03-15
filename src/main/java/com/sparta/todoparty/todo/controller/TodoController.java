package com.sparta.todoparty.todo.controller;


import com.sparta.todoparty.common.ResponseDto;
import com.sparta.todoparty.security.UserDetailsImpl;
import com.sparta.todoparty.todo.dto.MyTodoListResponseDto;
import com.sparta.todoparty.todo.dto.TodoRequestDto;
import com.sparta.todoparty.todo.dto.TodoResponseDto;
import com.sparta.todoparty.todo.service.TodoBusinessService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todos")
public class TodoController {

	private final TodoBusinessService todoBusinessService;


	@Transactional
	@PostMapping
	public ResponseEntity<ResponseDto> postTodo(
		@RequestBody @Valid TodoRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		//UserDetailsImpl에 Getter 추가

		TodoResponseDto responseDto = todoBusinessService.postTodo(requestDto,
			userDetails.getUserEntity());

		return ResponseEntity.ok()
			.body(ResponseDto.builder()
				.message("할일카드 작성 성공")
				.data(responseDto)
				.build());
	}

	@ResponseBody
	@GetMapping("/{todoId}")
	public ResponseEntity<ResponseDto> getTodoByTodoId(@PathVariable Long todoId) {

		TodoResponseDto responseDto = todoBusinessService.getTodoByTodoId(todoId);

		return ResponseEntity.ok()
			.body(ResponseDto.builder()
				.message("할일카드 조회 성공")
				.data(responseDto)
				.build());
	}

	@GetMapping("/mytodos")
	public ResponseEntity<ResponseDto<MyTodoListResponseDto>> getPostsByUserId(
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		MyTodoListResponseDto list = todoBusinessService.getTodosByUserId(userDetails.getUserEntity());
		return ResponseEntity.ok()
			.body(ResponseDto.<MyTodoListResponseDto>builder()
				.message("내가 작성한 할일카드 조회 성공")
				.data(list)
				.build());
	}

	//할일카드 수정
	@Transactional
	@PutMapping("/{todoId}")
	public ResponseEntity<ResponseDto> updateTodo(@PathVariable Long todoId,
		@RequestBody @Valid TodoRequestDto requestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {

		TodoResponseDto responseDto = todoBusinessService.updateTodo(todoId, requestDto,
			userDetails.getUserEntity());

		return ResponseEntity.ok()
			.body(ResponseDto.builder()
				.message("할일카드 수정 성공")
				.data(responseDto)
				.build());
	}

	//할일카드 삭제
	@Transactional
	@DeleteMapping("/{todoId}")
	public ResponseEntity<ResponseDto> deleteTodo(@PathVariable Long todoId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		todoBusinessService.deleteTodo(todoId, userDetails.getUserEntity());
		return ResponseEntity.ok()
			.body(ResponseDto.builder()
				.message("할일카드 삭제 성공")
				.data(null)
				.build());
	}

	@Transactional
	@PatchMapping("/{todoId}/complete")
	public ResponseEntity<ResponseDto> patchTodo(@PathVariable Long todoId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {

		TodoResponseDto responseDto = todoBusinessService.completeTodo(todoId,
			userDetails.getUserEntity());

		return ResponseEntity.ok()
			.body(ResponseDto.builder()
				.message("완료처리 성공")
				.data(responseDto)
				.build());
	}
}
