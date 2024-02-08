package com.sparta.todoparty.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.todoparty.dto.CommonResponseDto;
import com.sparta.todoparty.security.UserDetailsService;
import com.sparta.todoparty.service.UserService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

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

        if(Objects.nonNull(token)){
            if(jwtUtil.validateToken(token)){
                Claims info = jwtUtil.getUserInfoFromToken(token); //이 Claim이 유저정보를 담고 있음

                //인증정보에 유저정보(username) 넣기
                //username -> user 조회 -> userDetails 에 담고 -> authentication의 principal에 담고
                String username = info.getSubject();
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UserDetails userDetails = userDetailsService.getUserDetails(username);
                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                // -> securitycontent에 담고
                context.setAuthentication(authentication);
                // -> SecurityHolder에 담고
                SecurityContextHolder.setContext(context);
                // -> 이제 @AuthenticationPrincipal로 조회할 수 있음

            } else{
                //인증정보 존재하지 않을 때
                CommonResponseDto commonResponseDto = new CommonResponseDto("토큰이 유효하지 않습니다.", HttpStatus.BAD_REQUEST.value());
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.setContentType("application/json; charset=UTf-8");
                response.getWriter().write(objectMapper.writeValueAsString(commonResponseDto));
                return;
            }
        }
        //다음 필터 처리를 할 수 있게
        filterChain.doFilter(request, response);
    }
}
