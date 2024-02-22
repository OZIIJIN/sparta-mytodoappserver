package com.sparta.todoparty;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.todoparty.config.WebSecurityConfig;
import com.sparta.todoparty.controller.TodoController;
import com.sparta.todoparty.controller.UserController;
import com.sparta.todoparty.dto.TodoRequestDto;
import com.sparta.todoparty.entity.User;
import com.sparta.todoparty.mvc.MockSpringSecurityFilter;
import com.sparta.todoparty.security.UserDetailsImpl;
import com.sparta.todoparty.service.TodoService;
import com.sparta.todoparty.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(
        controllers = {TodoController.class},
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = WebSecurityConfig.class
                )
        }
)
public class UserTodoMvcTest {
    private MockMvc mvc;

    private Principal mockPrincipal;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    TodoService todoService;

    @MockBean
    UserService userService;

    @BeforeEach
    public void setup(){
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity(new MockSpringSecurityFilter()))
                .build();
    }

    private void mockUserSetuo(){
        String username = "yejin";
        String password = "yejin1234";
        User testUser = new User(username, password);
        UserDetailsImpl userDetails = new UserDetailsImpl(testUser);
        mockPrincipal = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
    @Test
    @DisplayName("할일카드 등록")
    void test1() throws Exception{
        //given
        this.mockUserSetuo();
        String title = "제목";
        String content = "내용";
        TodoRequestDto requestDto = new TodoRequestDto();
        requestDto.setTitle(title);
        requestDto.setContent(content);

        String postInfo = objectMapper.writeValueAsString(requestDto);

        //when - then
        mvc.perform(post("/api/todos")
                .content(postInfo)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .principal(mockPrincipal)
            )
            .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/api/todos/myTodos"))
                .andDo(print());
    }
}
