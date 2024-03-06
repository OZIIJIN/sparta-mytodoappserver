package com.sparta.todoparty.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.sparta.todoparty.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class JwtUtilTest implements CommonTest{

	@Autowired
	JwtUtil jwtUtil;

	@Mock
	private HttpServletRequest request;

	@BeforeEach
	void setUp() {
		jwtUtil.init();
	}//@BeforeEach를 사용해서 setUp

	@DisplayName("토큰 생성")
	@Test
	void createToken() {
		// when
		String token = jwtUtil.createToken(TEST_USER_NAME);

		// then
		assertNotNull(token);
	}

}
