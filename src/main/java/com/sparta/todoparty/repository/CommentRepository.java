package com.sparta.todoparty.repository;

import com.sparta.todoparty.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByTodoIdOrderByCreateDate(Long todoId);

    void deleteByTodoId(Long todoId);
}
