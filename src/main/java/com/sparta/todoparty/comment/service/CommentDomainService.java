package com.sparta.todoparty.comment.service;

import com.sparta.todoparty.comment.domain.CommentDomain;
import com.sparta.todoparty.comment.entity.CommentEntity;
import com.sparta.todoparty.comment.repository.CommentRespository;
import com.sparta.todoparty.exception.CommentNotFoundException;
import com.sparta.todoparty.todo.domain.TodoDomain;
import com.sparta.todoparty.todo.entity.TodoEntity;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.RejectedExecutionException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentDomainService {

	private final CommentRespository commentRespository;
	private final MessageSource messageSource;

	public CommentDomain save(CommentEntity commentEntity) {
		commentRespository.save(commentEntity);

		return CommentDomain.from(commentEntity);
	}
	public CommentEntity getCommentWithUser(Long commentId, Long userId) {
		CommentEntity commentEntity = commentRespository.findById(commentId)
			.orElseThrow(() -> new CommentNotFoundException(messageSource.getMessage(
					"not.found.comment",
					null,
					"Not Found Comment",
					Locale.getDefault()
				))
			);
		if (!userId.equals(commentEntity.getCreatedBy())) {
			throw new RejectedExecutionException("댓글의 작성자만 수정이 가능합니다.");
		}
		return commentEntity;
	}
	public CommentDomain updateComment(Long commentId, String newcontent, Long userId) {
		CommentEntity commentEntity = getCommentWithUser(commentId, userId);
		commentEntity.update(newcontent);
		return CommentDomain.from(commentEntity);
	}

	public void deleteComment(Long commentId, Long userId) {
		CommentEntity commentEntity = getCommentWithUser(commentId, userId);
		commentRespository.deleteComment(commentEntity);
	}

	public void deleteByTodoId(Long todoId) {
		commentRespository.deleteByRegisteredAt(todoId);
	}

	public List<CommentDomain> getCommentsBy(Long todoId) {
		List<CommentEntity> commentEntities = commentRespository.findAllByResiteredAt(todoId);
		return commentEntities.stream().map(CommentDomain::from).toList();
	}
}
