package com.sparta.todoparty.user.dto;

import com.sparta.todoparty.user.entity.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String username;

    public UserDto(UserEntity userEntity){
        this.username = userEntity.getUsername();
    }
}
