package com.sparta.todoparty.collaboration.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CollaborationRepositoryImpl implements CollaborationRepository{

	private final CollaborationJpaRepository collaborationJpaRepository;
}
