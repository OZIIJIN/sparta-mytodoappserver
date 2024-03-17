package com.sparta.todoparty.collaboration.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CollaborationRequestDto {

	@NotBlank
	private String todo;
}
