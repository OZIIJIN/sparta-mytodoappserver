package com.sparta.todoparty.user.domain;

import com.sparta.todoparty.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDomain {

	private Long userId;

	private String username;

	private String password;

	public static UserDomain from(UserEntity userEntity) {
		return new UserDomain(
			userEntity.getId(),
			userEntity.getUsername(),
			userEntity.getPassword()
		);
	}

}
