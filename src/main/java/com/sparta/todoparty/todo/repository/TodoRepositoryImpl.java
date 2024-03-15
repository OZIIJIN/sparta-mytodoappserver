package com.sparta.todoparty.todo.repository;

import static com.sparta.todoparty.comment.entity.QCommentEntity.commentEntity;
import static com.sparta.todoparty.todo.entity.QTodoEntity.todoEntity;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.todoparty.comment.entity.CommentEntity;
import com.sparta.todoparty.todo.dto.TodoResponseDto;
import com.sparta.todoparty.todo.dto.TodoWithComments;
import com.sparta.todoparty.todo.entity.TodoEntity;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TodoRepositoryImpl implements TodoRepository {

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
			.where(todoEntity.createdBy.eq(userId), todoEntity.iscompleted.eq(false))
			.orderBy(todoEntity.modifiedAt.desc())
			.fetch();
	}

	@Override
	public List<TodoEntity> findAllCompleted(Long userId) {
		return jpaQueryFactory.selectFrom(todoEntity)
			.where(todoEntity.createdBy.eq(userId), todoEntity.iscompleted.eq(true))
			.orderBy(todoEntity.modifiedAt.desc())
			.fetch();
	}

	@Override
	public List<TodoWithComments> getTodos(Long todoId) {
		List<Tuple> tuples = jpaQueryFactory
			.select(todoEntity, commentEntity)
			.from(todoEntity)
			.leftJoin(commentEntity).on(todoEntity.id.eq(commentEntity.registeredAt))
			.where(todoEntity.id.eq(todoId))
			.fetch();

		return getTodoWithComments(tuples);
	}

	@Override
	public List<TodoWithComments> getAllTodos() {
		List<Tuple> tuples = jpaQueryFactory
			.select(todoEntity, commentEntity)
			.from(todoEntity)
			.leftJoin(commentEntity).on(todoEntity.id.eq(commentEntity.registeredAt))
			.fetch();

		return getTodoWithComments(tuples);
	}

	private List<TodoWithComments> getTodoWithComments(List<Tuple> tuples) {
		Map<Long, TodoWithComments> todoMap = new HashMap<>();

		for (Tuple tuple : tuples) {
			TodoEntity todoEntity = tuple.get(0, TodoEntity.class);
			CommentEntity commentEntity = tuple.get(1, CommentEntity.class);

			TodoWithComments todoWithComments = todoMap.getOrDefault(todoEntity.getId(),
				new TodoWithComments(todoEntity, new ArrayList<>()));
			todoWithComments.getComments().add(commentEntity);
			todoMap.put(todoEntity.getId(), todoWithComments);
		}

		return new ArrayList<>(todoMap.values());
	}

}
