package com.sparta.todoparty.comment.repository;

import com.sparta.todoparty.comment.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentJpaRepository extends JpaRepository<CommentEntity, Long> {

    void deleteByRegisteredAt(Long todoId);

    List<CommentEntity> findAllByRegisteredAt(Long todoId);
}
