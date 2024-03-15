package com.sparta.todoparty.user.service;

import com.sparta.todoparty.user.dto.UserRequsetDto;
import com.sparta.todoparty.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserBusinessService {

    //postman에서 test할 때는 security 주석처리!!

    private final UserDomainService userDomainService;

    public void signup(UserRequsetDto userRequsetDto){

        userDomainService.signup(userRequsetDto);
    }

    public void login(UserRequsetDto userRequsetDto) {

        userDomainService.login(userRequsetDto);
    }
}
