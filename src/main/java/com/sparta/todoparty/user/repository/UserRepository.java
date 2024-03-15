package com.sparta.todoparty.user.repository;

import com.sparta.todoparty.user.entity.UserEntity;
import java.util.Optional;

public interface UserRepository {

	void
	save(UserEntity userEntity);

	Optional<UserEntity> findByUsername(String username);

	Optional<UserEntity> findById(Long userId);

}
