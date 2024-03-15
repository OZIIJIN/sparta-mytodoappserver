package com.sparta.todoparty.todo.dto;

import com.sparta.todoparty.comment.entity.CommentEntity;
import com.sparta.todoparty.todo.entity.TodoEntity;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TodoWithComments {
	private final TodoEntity todo;
	private final List<CommentEntity> comments;
}
