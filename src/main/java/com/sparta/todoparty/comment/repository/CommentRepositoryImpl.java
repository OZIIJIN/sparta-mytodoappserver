package com.sparta.todoparty.comment.repository;

import com.sparta.todoparty.comment.entity.CommentEntity;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRespository{

	private final CommentJpaRepository commentJpaRepository;

	@Override
	public CommentEntity save(CommentEntity commentEntity) {
		return commentJpaRepository.save(commentEntity);
	}

	@Override
	public Optional<CommentEntity> findById(Long commentId) {
		return commentJpaRepository.findById(commentId);
	}

	@Override
	public void deleteComment(CommentEntity commentEntity) {
		commentJpaRepository.delete(commentEntity);
	}

	@Override
	public void deleteByRegisteredAt(Long todoId) {
		commentJpaRepository.deleteByRegisteredAt(todoId);
	}

	@Override
	public List<CommentEntity> findAllByResiteredAt(Long todoId) {
		return commentJpaRepository.findAllByRegisteredAt(todoId);
	}
}
