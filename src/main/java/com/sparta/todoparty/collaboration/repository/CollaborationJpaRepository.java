package com.sparta.todoparty.collaboration.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CollaborationJpaRepository extends
	JpaRepository<com.sparta.todoparty.collaboration.entity.CollaborationEntity, Long> {

}
