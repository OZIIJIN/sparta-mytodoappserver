package com.sparta.todoparty.comment.repository;

import com.sparta.todoparty.comment.entity.CommentEntity;
import java.util.List;
import java.util.Optional;

public interface CommentRespository {

	CommentEntity save(CommentEntity commentEntity);
	Optional<CommentEntity> findById(Long commentId);

	void deleteComment(CommentEntity commentEntity);

	void deleteByRegisteredAt(Long todoId);

	List<CommentEntity> findAllByResiteredAt(Long todoId);
}
