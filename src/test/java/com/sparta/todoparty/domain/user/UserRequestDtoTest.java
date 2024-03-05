package com.sparta.todoparty.domain.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.sparta.todoparty.dto.UserRequsetDto;
import com.sparta.todoparty.test.CommonTest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class UserRequestDtoTest implements CommonTest {

	private <T> void setDto(T dto, String username, String password) {
		ReflectionTestUtils.setField(dto, "username", username);
		ReflectionTestUtils.setField(dto, "password", password);
	}

	@Nested
	class 유저_요청_DTO_생성_테스트 {

		@Test
		void 생성_성공() {
			// given
			UserRequsetDto userRequsetDto = new UserRequsetDto();
			setDto(userRequsetDto, TEST_USER_NAME, TEST_USER_PASSWORD);

			//when
			Set<ConstraintViolation<UserRequsetDto>> violations = validate(userRequsetDto);

			// then
			assertThat(violations).isEmpty();
			//비어있으면 아무것도 잘못된게 없음 -> 정상적으로 UserRequestDto를 생성함

		}

	}
	private Set<ConstraintViolation<UserRequsetDto>> validate(UserRequsetDto userRequestDTO) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		return validator.validate(userRequestDTO);
	}
}
