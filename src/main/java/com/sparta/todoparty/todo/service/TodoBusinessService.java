package com.sparta.todoparty.todo.service;

import com.querydsl.core.Tuple;
import com.sparta.todoparty.comment.domain.CommentDomain;
import com.sparta.todoparty.comment.dto.CommentResponseDto;
import com.sparta.todoparty.comment.service.CommentDomainService;
import com.sparta.todoparty.common.PageDTO;
import com.sparta.todoparty.todo.domain.TodoDomain;
import com.sparta.todoparty.todo.dto.TodoListResponseDto;
import com.sparta.todoparty.todo.dto.TodoRequestDto;
import com.sparta.todoparty.todo.dto.TodoResponseDto;
import com.sparta.todoparty.todo.dto.TodoSerchDto;
import com.sparta.todoparty.todo.dto.TodoWithComments;
import com.sparta.todoparty.todo.entity.TodoEntity;
import com.sparta.todoparty.user.domain.UserDomain;
import com.sparta.todoparty.user.entity.UserEntity;
import com.sparta.todoparty.user.service.UserDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.jaxb.SpringDataJaxb.PageDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoBusinessService {

	private final TodoDomainService todoDomainService;
	private final UserDomainService userDomainService;
	private final CommentDomainService commentDomainService;

	public TodoResponseDto postTodo(TodoRequestDto todoRequestDto, UserEntity userEntity) {
		UserDomain userDomain = userDomainService.getUser(userEntity.getUsername());
		TodoEntity todoEntity = new TodoEntity(todoRequestDto, userDomain.getUserId());

		TodoDomain savedTodo = todoDomainService.save(todoEntity);

		return new TodoResponseDto(savedTodo);
	}

//	public TodoResponseDto getTodoByTodoId(Long todoId) {
//		TodoDomain todoDomain = todoDomainService.getTodo(todoId);
//		List<CommentDomain> commentDomains = commentDomainService.getCommentsBy(todoId);
//		List<CommentResponseDto> commentResponseDtos = commentDomains.stream().map(
//			CommentResponseDto::new).toList();
//		TodoResponseDto todoResponseDto = new TodoResponseDto(todoDomain);
//		todoResponseDto.addList(commentResponseDtos);
//		return todoResponseDto;
//	}


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
		commentDomainService.deleteByTodoId(todoId);
		todoDomainService.delete(todoId, userDomain.getUserId());
	}

	public TodoListResponseDto getTodosByUserId(UserEntity userEntity) {
		UserDomain userDomain = userDomainService.getUser(userEntity.getUsername());

		List<TodoDomain> todoDomainList = todoDomainService.getTodosByUserId(
			userDomain.getUserId());

		List<TodoResponseDto> todoResponseDtos = todoDomainList.stream().map(TodoResponseDto::new)
			.toList();

		return new TodoListResponseDto(todoResponseDtos);
	}

	public TodoListResponseDto getCompletedTodos(UserEntity userEntity) {
		UserDomain userDomain = userDomainService.getUser(userEntity.getUsername());

		List<TodoDomain> todoDomainList = todoDomainService.getCompletedTodos(
			userDomain.getUserId());

		List<TodoResponseDto> todoResponseDtos = todoDomainList.stream().map(TodoResponseDto::new)
			.toList();

		return new TodoListResponseDto(todoResponseDtos);
	}

	public List<TodoWithComments> getTodos(Long todoId) {
		return todoDomainService.getTodos(todoId);
	}
}
