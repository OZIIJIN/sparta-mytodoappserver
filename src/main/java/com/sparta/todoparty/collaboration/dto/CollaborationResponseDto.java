package com.sparta.todoparty.collaboration.dto;

import com.sparta.todoparty.collaboration.domain.CollaborationDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CollaborationResponseDto {

	private Long collaborationId;

	private Long fromUserId;

	private Long toUserId;

	private String todo;

	private Boolean isAccepted;

	private Boolean isCompleted;

	public CollaborationResponseDto(CollaborationDomain collaborationDomain) {
		this.collaborationId = collaborationDomain.getCollaborationId();
		this.fromUserId = collaborationDomain.getFromUserId();
		this.toUserId = collaborationDomain.getToUserId();
		this.todo = collaborationDomain.getTodo();
		this.isAccepted = collaborationDomain.getIsAccepted();
		this.isCompleted = collaborationDomain.getIsCompleted();
	}
}
