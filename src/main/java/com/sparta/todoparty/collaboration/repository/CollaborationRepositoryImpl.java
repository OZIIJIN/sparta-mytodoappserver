package com.sparta.todoparty.collaboration.repository;

import com.sparta.todoparty.collaboration.entity.CollaborationEntity;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CollaborationRepositoryImpl implements CollaborationRepository{

	private final CollaborationJpaRepository collaborationJpaRepository;

	@Override
	public CollaborationEntity save(CollaborationEntity collaborationEntity) {
		return collaborationJpaRepository.save(collaborationEntity);
	}
}
