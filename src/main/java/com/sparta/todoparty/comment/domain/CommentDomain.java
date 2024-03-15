package com.sparta.todoparty.comment.domain;

import com.sparta.todoparty.comment.entity.CommentEntity;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDomain {

	private Long commentId;

	private String content;

	private Long createdBy;

	private Long registeredAt;

	private LocalDateTime createdAt;

	private LocalDateTime modifiedAt;

	public static CommentDomain from(CommentEntity commentEntity) {
		return new CommentDomain(
			commentEntity.getId(),
			commentEntity.getContent(),
			commentEntity.getCreatedBy(),
			commentEntity.getRegisteredAt(),
			commentEntity.getCreatedAt(),
			commentEntity.getModifiedAt()
		);
	}

}
