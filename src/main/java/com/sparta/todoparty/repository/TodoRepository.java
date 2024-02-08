package com.sparta.todoparty.repository;

import com.sparta.todoparty.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
