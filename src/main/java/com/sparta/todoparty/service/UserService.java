package com.sparta.todoparty.service;

import com.sparta.todoparty.dto.UserRequsetDto;
import com.sparta.todoparty.entity.User;
import com.sparta.todoparty.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    //postman에서 test할 때는 security 주석처리!!
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public void signup(UserRequsetDto requsetDto){
        //username과 password를 받아야함, password는 passwordencoder를 통해서 받아야함
        String username = requsetDto.getUsername();
        String password = passwordEncoder.encode(requsetDto.getPassword());

        //DB에 존재하는지 확인
        //Optional이기 때문에 isPresent()를 사용할 수 있음
        if(userRepository.findByUsername(username).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 유저입니다.");
        }

        //User 만들기 및 repository에 저장
        User user = new User(username, password);
        userRepository.save(user);
    }
}
