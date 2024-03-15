package com.sparta.todoparty.todo.repository;

import com.querydsl.core.Tuple;
import com.sparta.todoparty.todo.dto.TodoWithComments;
import com.sparta.todoparty.todo.entity.TodoEntity;
import java.util.List;
import java.util.Optional;

public interface TodoRepository {

	Optional<TodoEntity> findById(Long todoId);

	TodoEntity save(TodoEntity todoEntity);

	void delete(TodoEntity todoEntity);

	List<TodoEntity> findAllById(Long userId);

	List<TodoEntity> findAllCompleted(Long userId);

	List<TodoWithComments> getTodos(Long todoId);

	List<TodoWithComments> getAllTodos();
}
