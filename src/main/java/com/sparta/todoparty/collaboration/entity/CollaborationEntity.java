package com.sparta.todoparty.collaboration.entity;

import com.sparta.todoparty.common.TimeStamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "tb_collaboration")
public class CollaborationEntity extends TimeStamp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Long fromUserId;

	@Column(nullable = false)
	private Long toUserId;

	@Column(nullable = false)
	private String todo;

	@Column
	private Boolean isAccepted;

	@Column
	private Boolean isCompleted;


	@Builder
	public CollaborationEntity(Long fromUserId, Long toUserId, String todo) {
		this.fromUserId = fromUserId;
		this.toUserId = toUserId;
		this.todo = todo;
		this.isAccepted = false;
		this.isCompleted = false;
	}

	public void updateTodo(String newTodo) {
		this.todo = newTodo;
	}

	public void completed() {
		this.isCompleted = true;
	}
}
