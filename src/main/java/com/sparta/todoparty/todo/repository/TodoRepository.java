package com.sparta.todoparty.todo.repository;

import com.sparta.todoparty.todo.entity.TodoEntity;
import java.util.List;
import java.util.Optional;

public interface TodoRepository {

	Optional<TodoEntity> findById(Long todoId);

	TodoEntity save(TodoEntity todoEntity);

	void delete(TodoEntity todoEntity);

	List<TodoEntity> findAllById(Long userId);
}
