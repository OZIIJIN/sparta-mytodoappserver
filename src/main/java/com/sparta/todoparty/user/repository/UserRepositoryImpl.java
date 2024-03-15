package com.sparta.todoparty.user.repository;

import com.sparta.todoparty.user.entity.UserEntity;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository{

	private final UserJpaRepository userJpaRepository;

	@Override
	public void save(UserEntity userEntity) {
		userJpaRepository.save(userEntity);
	}

	@Override
	public Optional<UserEntity> findByUsername(String username) {
		return userJpaRepository.findByUsername(username);
	}

	@Override
	public Optional<UserEntity> findById(Long userId) {
		return userJpaRepository.findById(userId);
	}
}
