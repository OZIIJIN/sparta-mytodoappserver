package com.sparta.todoparty.collaboration.service;

import com.sparta.todoparty.collaboration.domain.CollaborationDomain;
import com.sparta.todoparty.collaboration.dto.CollaborationResponseDto;
import com.sparta.todoparty.collaboration.entity.CollaborationEntity;
import com.sparta.todoparty.user.domain.UserDomain;
import com.sparta.todoparty.user.entity.UserEntity;
import com.sparta.todoparty.user.service.UserDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CollaborationBusinessService {

	private final CollaborationDomainService collaborationDomainService;
	private final UserDomainService userDomainService;

	public CollaborationResponseDto createCollaboration(String todo, Long toUser,
		UserEntity userEntity) {
		UserDomain fromUserDomain = userDomainService.getUser(userEntity.getUsername());
		UserDomain toUserDomain = userDomainService.getUserBy(toUser);

		Long fromUserId = fromUserDomain.getUserId();
		Long toUserId = toUserDomain.getUserId();

		CollaborationEntity collaborationEntity = new CollaborationEntity(fromUserId, toUserId,
			todo);

		CollaborationDomain savedCollaboration = collaborationDomainService.save(
			collaborationEntity);

		return new CollaborationResponseDto(savedCollaboration);
	}
}
