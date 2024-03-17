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
}
