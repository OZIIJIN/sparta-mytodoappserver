package com.sparta.todoparty.todo.repository;

import com.sparta.todoparty.todo.entity.TodoEntity;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoRepository{

	private final TodoJpaRepository jpaRepository;

	@Override
	public Optional<TodoEntity> findById(Long todoId) {
		return jpaRepository.findById(todoId);
	}

	@Override
	public TodoEntity save(TodoEntity todoEntity) {
		return jpaRepository.save(todoEntity);
	}

	@Override
	public void delete(TodoEntity todoEntity) {
		jpaRepository.delete(todoEntity);
	}

	@Override
	public List<TodoEntity> findAllById(Long userId) {
		return jpaRepository.findAllByCreatedBy(userId);
	}
}
