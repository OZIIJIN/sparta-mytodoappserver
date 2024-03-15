package com.sparta.todoparty.comment.repository;

import com.sparta.todoparty.comment.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByTodoIdOrderByCreateDate(Long todoId);

    void deleteByTodoId(Long todoId);
}
