package com.sparta.todoparty.collaboration.service;

import com.sparta.todoparty.collaboration.domain.CollaborationDomain;
import com.sparta.todoparty.collaboration.entity.CollaborationEntity;
import com.sparta.todoparty.collaboration.repository.CollaborationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CollaborationDomainService {

	private final CollaborationRepository collaborationRepository;

	public CollaborationDomain save(CollaborationEntity collaborationEntity) {

		CollaborationEntity saved = collaborationRepository.save(collaborationEntity);

		return CollaborationDomain.from(saved);
	}

	public CollaborationEntity getCollaboBy(Long id) {
		CollaborationEntity collaboration = collaborationRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("등록된 콜라보가 없습니다."));
		return collaboration;
	}

	public CollaborationDomain acceptCollaboration(Long collaborationId) {

		CollaborationEntity collaboration = getCollaboBy(collaborationId);

		collaboration.accepted();

		return CollaborationDomain.from(collaboration);
	}

	public CollaborationDomain completeCollaboration(Long collaborationId) {

		CollaborationEntity collaboration = getCollaboBy(collaborationId);

		collaboration.completed();

		return CollaborationDomain.from(collaboration);
	}
}
