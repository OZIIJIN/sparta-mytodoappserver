package com.sparta.todoparty.todo.service;

import com.querydsl.core.Tuple;
import com.sparta.todoparty.common.PageDTO;
import com.sparta.todoparty.todo.domain.TodoDomain;
import com.sparta.todoparty.todo.dto.TodoSerchDto;
import com.sparta.todoparty.todo.dto.TodoWithComments;
import com.sparta.todoparty.todo.entity.TodoEntity;
import com.sparta.todoparty.todo.repository.TodoRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoDomainService {

	private final TodoRepository todoRepository;

	public TodoEntity getTodoEntityWithUser(Long todoId, Long userId) {
		TodoEntity todoEntity = todoRepository.findById(todoId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 할일 id 입니다."));

		if(!userId.equals(todoEntity.getCreatedBy())) {
			throw new RejectedExecutionException("할일카드의 작성자만 해당 작업이 가능합니다.");
		}

		return todoEntity;
	}
	public TodoDomain getTodo(Long todoId) {
		TodoEntity todoEntity = todoRepository.findById(todoId)
			.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 할일 id 입니다."));

		return TodoDomain.from(todoEntity);
	}

	public TodoDomain save(TodoEntity todoEntity) {
		TodoEntity savedEntity = todoRepository.save(todoEntity);

		return TodoDomain.from(savedEntity);
	}

	public TodoDomain updateTodo(Long todoId, String newTitle, String newContent, Long userId) {
		TodoEntity todoEntity = getTodoEntityWithUser(todoId, userId);

		todoEntity.update(newTitle, newContent);

		return TodoDomain.from(todoEntity);
	}

	public void delete(Long todoId, Long userId) {
		TodoEntity todoEntity = getTodoEntityWithUser(todoId, userId);

		todoRepository.delete(todoEntity);
	}

	public TodoDomain completedTodo(Long todoId, Long userId) {
		TodoEntity todoEntity = getTodoEntityWithUser(todoId, userId);

		todoEntity.complete();

		return TodoDomain.from(todoEntity);
	}

	public List<TodoDomain> getTodosByUserId(Long userId) {
		List<TodoEntity> todos = todoRepository.findAllById(userId);

		return todos.stream().map(TodoDomain::from).toList();
	}

	public List<TodoDomain> getCompletedTodos(Long userId) {
		List<TodoEntity> todos = todoRepository.findAllCompleted(userId);

		return todos.stream().map(TodoDomain::from).toList();
	}

	public List<TodoWithComments> getTodos(Long todoId) {
		return todoRepository.getTodos(todoId);
	}
}
