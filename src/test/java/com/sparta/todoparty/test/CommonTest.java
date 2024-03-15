package com.sparta.todoparty.test;


import com.sparta.todoparty.user.entity.UserEntity;

public interface CommonTest {
	String ANOTHER_PREFIX = "another-";
	Long TEST_USER_ID = 1L;
	Long TEST_ANOTHER_USER_ID = 2L;
	String TEST_USER_NAME = "username";
	String TEST_USER_PASSWORD = "password";

	UserEntity TEST_USER_ENTITY = UserEntity.builder()
		.username(TEST_USER_NAME)
		.password(TEST_USER_PASSWORD)
		.build();

	UserEntity TEST_ANOTHER_USER_ENTITY = UserEntity.builder()
		.username(ANOTHER_PREFIX + TEST_USER_NAME)
		.password(ANOTHER_PREFIX + TEST_USER_PASSWORD)
		.build();
}
