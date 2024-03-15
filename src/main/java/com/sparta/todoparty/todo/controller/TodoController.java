package com.sparta.todoparty.todo.controller;


import com.querydsl.core.Tuple;
import com.sparta.todoparty.common.PageDTO;
import com.sparta.todoparty.common.ResponseDto;
import com.sparta.todoparty.security.UserDetailsImpl;
import com.sparta.todoparty.todo.dto.TodoListResponseDto;
import com.sparta.todoparty.todo.dto.TodoRequestDto;
import com.sparta.todoparty.todo.dto.TodoResponseDto;
import com.sparta.todoparty.todo.dto.TodoWithComments;
import com.sparta.todoparty.todo.entity.TodoEntity;
import com.sparta.todoparty.todo.service.TodoBusinessService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

//	@GetMapping("/{todoId}")
//	public ResponseEntity<ResponseDto> getTodoByTodoId(@PathVariable Long todoId) {
//
//		TodoResponseDto responseDto = todoBusinessService.getTodoByTodoId(todoId);
//
//		return ResponseEntity.ok()
//			.body(ResponseDto.builder()
//				.message("할일카드 조회 성공")
//				.data(responseDto)
//				.build());
//	}

	@GetMapping("/{todoId}")
	public ResponseEntity<ResponseDto<?>> getTodoById(@PathVariable Long todoId) {
		List<TodoWithComments> list = todoBusinessService.getTodos(todoId);
		return ResponseEntity.ok()
			.body(ResponseDto.builder()
				.message("조회 성공")
				.data(list)
				.build());
	}

	@GetMapping
	public ResponseEntity<ResponseDto<?>> getAllTodos() {
		List<TodoWithComments> list = todoBusinessService.getAllTodos();
		return ResponseEntity.ok()
			.body(ResponseDto.builder()
				.message("조회 성공")
				.data(list)
				.build());
	}

	@GetMapping("/my-todos")
	public ResponseEntity<ResponseDto<TodoListResponseDto>> getMyTodos(
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		TodoListResponseDto list = todoBusinessService.getTodosByUserId(
			userDetails.getUserEntity());
		return ResponseEntity.ok()
			.body(ResponseDto.<TodoListResponseDto>builder()
				.message("내가 작성한 할일카드 조회 성공")
				.data(list)
				.build());
	}

	@GetMapping("/complete")
	public ResponseEntity<ResponseDto<TodoListResponseDto>> getCompletedTodos(
		@AuthenticationPrincipal UserDetailsImpl userDetails) {
		TodoListResponseDto list = todoBusinessService.getCompletedTodos(
			userDetails.getUserEntity());

		return ResponseEntity.ok()
			.body(ResponseDto.<TodoListResponseDto>builder()
				.message("완료된 할일카드 조회 성공")
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
