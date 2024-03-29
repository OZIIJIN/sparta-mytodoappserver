package com.sparta.todoparty.test;

import static com.sparta.todoparty.jwt.JwtUtil.BEARER_PREFIX;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

import com.sparta.todoparty.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

	@DisplayName("토큰 추출")
	@Test
	void resolveToken() {
		// given
		var token = "test-token";
		var bearerToken = BEARER_PREFIX + token;

		// when
		given(request.getHeader(JwtUtil.AUTHORIZATION_HEADER)).willReturn(bearerToken);
		var resolvedToken = jwtUtil.resolveToken(request);

		// then
		assertEquals(token, resolvedToken);
	}

	@DisplayName("토큰 검증")
	@Nested
	class validateToken {

		@DisplayName("토큰 검증 성공")
		@Test
		void validateToken_success() {
			// given
			String token = jwtUtil.createToken(TEST_USER_NAME).substring(7);

			// when
			boolean isValid = jwtUtil.validateToken(token);

			// then
			assertTrue(isValid);
		}

		@DisplayName("토큰 검증 실패 - 유효하지 않은 토큰")
		@Test
		void validateToken_fail() {
			// given
			String invalidToken = "invalid-token";

			// when
			boolean isValid = jwtUtil.validateToken(invalidToken);

			// then
			assertFalse(isValid);
		}
	}

	@DisplayName("토큰에서 UserInfo 조회")
	@Test
	void getUserInfoFromToken() {
		// given
		String token = jwtUtil.createToken(TEST_USER_NAME).substring(7);

		// when
		Claims claims = jwtUtil.getUserInfoFromToken(token);
		// then
		assertNotNull(claims);
		assertEquals(TEST_USER_NAME, claims.getSubject());
	}

}
