package com.sparta.todoparty.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.todoparty.security.UserDetailsService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final UserDetailsService userDetailsService;
    public JwtAuthorizationFilter(JwtUtil jwtUtil, ObjectMapper objectMapper, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        //requset에서 Token 뽑아오기
        String token = jwtUtil.resolveToken(request);

        if(StringUtils.hasText(token)){

            //문제 없으면 토큰에서 이용자 정보만 info에 저장
            try {
                Claims info = jwtUtil.getUserInfoFromToken(token); //이 Claim이 유저정보를 담고 있음

                //username -> user 조회 -> userDetails 에 담고 -> authentication의 principal에 담고

                String username = info.getSubject();
                setAuthentication(username);
            } catch (Exception e) {
                log.error(e.getMessage());
                return;
            }

        }
        //다음 필터 처리를 할 수 있게
        filterChain.doFilter(request, response);
    }

    //인증 처리
    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        // -> securitycontent에 담고
        context.setAuthentication(authentication);
        // -> SecurityHolder에 담고
        SecurityContextHolder.setContext(context);
        // -> 이제 @AuthenticationPrincipal로 조회할 수 있음

    }

    //인증 객체 생성
    private Authentication createAuthentication(String username) {
        UserDetails userDetails = userDetailsService.getUser(username);
        //권한은 따로 설정안함
        return new UsernamePasswordAuthenticationToken(userDetails,
            null, null);
    }
}
