package com.sparta.todoparty.todo.service;

import com.sparta.todoparty.todo.domain.TodoDomain;
import com.sparta.todoparty.todo.dto.MyTodoListResponseDto;
import com.sparta.todoparty.todo.dto.TodoRequestDto;
import com.sparta.todoparty.todo.dto.TodoResponseDto;
import com.sparta.todoparty.todo.entity.TodoEntity;
import com.sparta.todoparty.user.domain.UserDomain;
import com.sparta.todoparty.user.entity.UserEntity;
import com.sparta.todoparty.user.service.UserDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoBusinessService {

	private final TodoDomainService todoDomainService;
	private final UserDomainService userDomainService;

	public TodoResponseDto postTodo(TodoRequestDto todoRequestDto, UserEntity userEntity) {
		UserDomain userDomain = userDomainService.getUser(userEntity.getUsername());
		TodoEntity todoEntity = new TodoEntity(todoRequestDto, userDomain.getUserId());

		TodoDomain savedTodo = todoDomainService.save(todoEntity);

		return new TodoResponseDto(savedTodo);
	}

	public TodoResponseDto getTodoByTodoId(Long todoId) {
		TodoDomain todoDomain = todoDomainService.getTodo(todoId);
		return new TodoResponseDto(todoDomain);
	}


	//할일카드 수정
	@Transactional
	public TodoResponseDto updateTodo(Long todoId, TodoRequestDto requestDto,
		UserEntity userEntity) {

		String newtitle = requestDto.getTitle();
		String newContent = requestDto.getContent();
		UserDomain userDomain = userDomainService.getUser(userEntity.getUsername());

		TodoDomain todoDomain = todoDomainService.updateTodo(todoId, newtitle, newContent,
			userDomain.getUserId());

		return new TodoResponseDto(todoDomain);
	}

	@Transactional
	public TodoResponseDto completeTodo(Long todoId, UserEntity userEntity) {
		UserDomain userDomain = userDomainService.getUser(userEntity.getUsername());
		TodoDomain todoDomain = todoDomainService.completedTodo(todoId, userDomain.getUserId());

		return new TodoResponseDto(todoDomain);
	}

	public void deleteTodo(Long todoId, UserEntity userEntity) {
		UserDomain userDomain = userDomainService.getUser(userEntity.getUsername());
		todoDomainService.delete(todoId, userDomain.getUserId());
	}

	public MyTodoListResponseDto getTodosByUserId(UserEntity userEntity) {
		UserDomain userDomain = userDomainService.getUser(userEntity.getUsername());

		List<TodoDomain> todoDomainList = todoDomainService.getTodosByUserId(
			userDomain.getUserId());

		List<TodoResponseDto> todoResponseDtos = todoDomainList.stream().map(TodoResponseDto::new)
			.toList();

		return new MyTodoListResponseDto(todoResponseDtos);
	}
}
