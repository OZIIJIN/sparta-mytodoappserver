package com.sparta.todoparty.security;

import com.sparta.todoparty.user.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService {

    public UserDetails getUser(String username) throws UsernameNotFoundException{
       User user = User.builder().username(username).build();
        //여기서 return 할때 User를 사용하려면 파라미터를 다 넣어줘야하지만 쉽지 않음 => UsrDetailsImpl 만들기
        return new UserDetailsImpl(user);
    }
}
