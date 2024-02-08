package com.sparta.todoparty.security;

import com.sparta.todoparty.entity.User;
import com.sparta.todoparty.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails getUserDetails(String username){
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not Found" + username));

        //여기서 return 할때 User를 사용하려면 파라미터를 다 넣어줘야하지만 쉽지 않음 => UsrDetailsImpl 만들기
        return new UserDetailsImpl(user);
    }
}
