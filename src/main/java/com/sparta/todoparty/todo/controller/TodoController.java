package com.sparta.todoparty.todo.controller;


import com.sparta.todoparty.common.ResponseDto;
import com.sparta.todoparty.security.UserDetailsImpl;
import com.sparta.todoparty.todo.dto.TodoRequestDto;
import com.sparta.todoparty.todo.dto.TodoResponseDto;
import com.sparta.todoparty.todo.service.TodoBusinessService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;

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

//	@ResponseBody
//	@GetMapping("/myTodos")
//	public List<TodoResponseDto> getPostsByUserId(
//		@AuthenticationPrincipal UserDetailsImpl userDetails) {
//		return todoBusinessService.getTodosByUserId(userDetails.getUserEntity());
//	}

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
