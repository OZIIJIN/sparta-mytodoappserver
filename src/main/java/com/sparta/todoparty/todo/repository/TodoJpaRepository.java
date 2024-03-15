package com.sparta.todoparty.todo.repository;

import com.sparta.todoparty.todo.entity.TodoEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoJpaRepository extends JpaRepository<TodoEntity, Long> {

    Optional<TodoEntity> findById(Long todoId);

    List<TodoEntity> findAllByCreatedBy(Long userId);
}
