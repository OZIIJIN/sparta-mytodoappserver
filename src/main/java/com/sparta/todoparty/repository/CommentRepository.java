package com.sparta.todoparty.repository;

import com.sparta.todoparty.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
