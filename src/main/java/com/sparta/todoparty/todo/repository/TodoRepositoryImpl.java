package com.sparta.todoparty.todo.repository;

import static com.sparta.todoparty.todo.entity.QTodoEntity.todoEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.todoparty.todo.entity.TodoEntity;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoRepository{

	private final TodoJpaRepository jpaRepository;

	private final JPAQueryFactory jpaQueryFactory;

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
		return jpaQueryFactory.selectFrom(todoEntity)
			.where(todoEntity.createdBy.eq(userId),todoEntity.iscompleted.eq(false))
			.orderBy(todoEntity.modifiedAt.desc())
			.fetch();
	}

	@Override
	public List<TodoEntity> findAllCompleted(Long userId) {
		return jpaQueryFactory.selectFrom(todoEntity)
			.where(todoEntity.createdBy.eq(userId),todoEntity.iscompleted.eq(true))
			.orderBy(todoEntity.modifiedAt.desc())
			.fetch();
	}

}
