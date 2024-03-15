package com.sparta.todoparty.todo.dto;

import com.sparta.todoparty.comment.dto.CommentResponseDto;
import com.sparta.todoparty.comment.entity.CommentEntity;
import com.sparta.todoparty.todo.domain.TodoDomain;
import com.sparta.todoparty.todo.entity.TodoEntity;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TodoWithComments {
	private final TodoResponseDto todo;
	private final List<CommentResponseDto> comments;
}
