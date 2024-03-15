package com.sparta.todoparty.todo.domain;

import com.sparta.todoparty.todo.entity.TodoEntity;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TodoDomain {

	private Long todoId;

	private String title;

	private String content;

	private LocalDateTime createAt;

	private LocalDateTime modifiedAt;

	private Long createdBy;

	private Boolean isCompleted;

	public static TodoDomain from(TodoEntity todoEntity) {
		return new TodoDomain(
			todoEntity.getId(),
			todoEntity.getTitle(),
			todoEntity.getContent(),
			todoEntity.getCreatedAt(),
			todoEntity.getModifiedAt(),
			todoEntity.getCreatedBy(),
			todoEntity.getIscompleted()
		);
	}

}
