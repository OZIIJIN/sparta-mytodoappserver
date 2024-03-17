package com.sparta.todoparty.user.service;

import com.sparta.todoparty.user.domain.UserDomain;
import com.sparta.todoparty.user.dto.UserRequsetDto;
import com.sparta.todoparty.user.entity.UserEntity;
import com.sparta.todoparty.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDomainService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserDomain getUser(String username) {
		UserEntity user = userRepository.findByUsername(username)
			.orElseThrow(() -> new IllegalArgumentException("등록된 유저가 없습니다."));
		return UserDomain.from(user);
	}

	public UserDomain getUserBy(Long id) {
		UserEntity user = userRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("등록된 유저가 없습니다."));
		return UserDomain.from(user);
	}

	public void signup(UserRequsetDto requsetDto) {
		//username과 password를 받아야함, password는 passwordencoder를 통해서 받아야함
		String username = requsetDto.getUsername();
		String password = passwordEncoder.encode(requsetDto.getPassword());

		validateUserDuplicate(username);

		UserEntity userEntity = new UserEntity(username, password);

		userRepository.save(userEntity);
	}
	public void validateUserDuplicate(String username) {
		if(userRepository.findByUsername(username).isPresent()){
			throw new IllegalArgumentException("이미 존재하는 유저입니다.");
		}
	}

	public void login(UserRequsetDto userRequsetDto) {
		String username = userRequsetDto.getUsername();
		String password = userRequsetDto.getPassword();

		UserEntity userEntity = userRepository.findByUsername(username)
			.orElseThrow(() -> new IllegalArgumentException("등록된 유저가 없습니다."));

		//패스워드가 등록된 패스워드와 일치한지 확인
		if(!passwordEncoder.matches(password, userEntity.getPassword())){
			throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
		}
	}
}
