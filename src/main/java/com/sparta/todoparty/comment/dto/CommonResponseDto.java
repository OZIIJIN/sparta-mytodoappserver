package com.sparta.todoparty.comment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // -> JSON으로 받아올 때 null인 것들을 빼고 받아옴
public class CommonResponseDto {

    private String msg;
    private int statusCode;
}
