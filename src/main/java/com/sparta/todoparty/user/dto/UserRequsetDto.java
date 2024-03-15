package com.sparta.todoparty.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
public class UserRequsetDto {

    @NotBlank(message = "username을 입력하세요.")
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "a-z, 0-9, 글자길이 4-10")
    private String username;

    @NotBlank(message = "password를 입력하세요.")
    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$", message = "a-z, A-z, 0-9, 글자길이 8-15")
    private String password;
}
