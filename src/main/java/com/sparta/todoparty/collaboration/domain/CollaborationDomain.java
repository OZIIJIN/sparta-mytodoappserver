package com.sparta.todoparty.collaboration.domain;

import com.sparta.todoparty.collaboration.entity.CollaborationEntity;
import com.sparta.todoparty.comment.domain.CommentDomain;
import com.sparta.todoparty.comment.entity.CommentEntity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CollaborationDomain {

	private Long collaborationId;

	private Long fromUserId;

	private Long toUserId;

	private String todo;

	private Boolean isAccepted;

	private Boolean isCompleted;

	public static CollaborationDomain from(CollaborationEntity collaboration) {
		return new CollaborationDomain(
			collaboration.getId(),
			collaboration.getFromUserId(),
			collaboration.getToUserId(),
			collaboration.getTodo(),
			collaboration.getIsAccepted(),
			collaboration.getIsCompleted()
		);
	}

}
