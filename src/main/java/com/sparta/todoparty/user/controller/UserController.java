package com.sparta.todoparty.user.controller;

import com.sparta.todoparty.common.ResponseDto;
import com.sparta.todoparty.user.dto.UserRequsetDto;
import com.sparta.todoparty.jwt.JwtUtil;
import com.sparta.todoparty.user.service.UserBusinessService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserBusinessService userBusinessService;
	private final JwtUtil jwtUtil;

	public UserController(UserBusinessService userBusinessService, JwtUtil jwtUtil) {
		this.userBusinessService = userBusinessService;
		this.jwtUtil = jwtUtil;
	}

	@PostMapping("/signup")
	public ResponseEntity<ResponseDto> signup(
		@RequestBody @Valid UserRequsetDto userRequestDto) {

		userBusinessService.signup(userRequestDto);

		return ResponseEntity.ok()
			.body(ResponseDto.builder()
				.message("회원 가입 성공")
				.data(null)
				.build());
	}

	@PostMapping("/login")
	public ResponseEntity<ResponseDto> login(
		@RequestBody @Valid UserRequsetDto userRequsetDto) {
		userBusinessService.login(userRequsetDto);

		return ResponseEntity.ok()
			.header(HttpHeaders.AUTHORIZATION,
				jwtUtil.createToken(userRequsetDto.getUsername()))
			.body(ResponseDto.<Void>builder()
				.message("로그인 성공")
				.build());
	}

}
