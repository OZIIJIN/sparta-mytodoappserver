package com.sparta.todoparty.collaboration.repository;

import com.sparta.todoparty.collaboration.entity.CollaborationEntity;
import java.util.Optional;

public interface CollaborationRepository {

	CollaborationEntity save(CollaborationEntity collaborationEntity);

	Optional<CollaborationEntity> findById(Long id);
}
