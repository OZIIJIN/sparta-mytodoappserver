package com.sparta.todoparty.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {

    @NotBlank(message = "내용을 입력하세요.")
    private String content;

}
