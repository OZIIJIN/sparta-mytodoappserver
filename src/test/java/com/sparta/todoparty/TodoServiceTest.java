package com.sparta.todoparty;

import com.sparta.todoparty.dto.TodoRequestDto;
import com.sparta.todoparty.dto.TodoResponseDto;
import com.sparta.todoparty.entity.Todo;
import com.sparta.todoparty.repository.TodoRepository;
import com.sparta.todoparty.security.UserDetailsImpl;
import com.sparta.todoparty.service.CommentService;
import com.sparta.todoparty.service.TodoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.concurrent.RejectedExecutionException;

import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class) //@Mock 사용을 위해 설정
public class TodoServiceTest {

    @Mock
    TodoRepository todoRepository;

    @Mock
    CommentService commentService;

    @Mock
    UserDetailsImpl userDetails;

    @Test
    @DisplayName("postTodo 메서드 - 할일카드 등록 성공")
    void test1() {
        //given
        String title = "제목";
        String content = "내용";

        TodoRequestDto todoRequestDto = new TodoRequestDto();
        todoRequestDto.setTitle(title);
        todoRequestDto.setContent(content);

        TodoService todoService = new TodoService(todoRepository, commentService);

        //when
        TodoResponseDto result = todoService.postTodo(todoRequestDto, userDetails);

        //then
        assertEquals(todoRequestDto.getTitle(), result.getTitle());
        assertEquals(todoRequestDto.getContent(), result.getContent());
    }

    @Test
    @DisplayName("할일카드 조회 성공")
    void test2() {
        //given
        Long id = 1L;
        String title = "제목";
        String content = "내용";

        TodoRequestDto todoRequestDto = new TodoRequestDto();
        todoRequestDto.setTitle(title);
        todoRequestDto.setContent(content);

        Todo todo = new Todo(todoRequestDto, userDetails);

        TodoService todoService = new TodoService(todoRepository, commentService);
        given(todoRepository.findById(id)).willReturn(Optional.of(todo));

        //when
        TodoResponseDto result = todoService.getTodoByTodoId(id);

        //then
        assertNotNull(result);
    }

    @Test
    @DisplayName("할일카드 조회 실패")
    void Test3(){
        //given
        Long id = 1L;

        TodoService todoService = new TodoService(todoRepository, commentService);

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, ()-> {
            todoService.getTodoByTodoId(id);
        });

        //then
        assertEquals("존재하지 않는 할일 id 입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("할일카드 수정 성공")
    void Test4() {
        //given
        Long id = 100L;
        String title = "제목";
        String content = "내용";
        String username = "yejin";
        TodoRequestDto requestDto = new TodoRequestDto();
        requestDto.setTitle(title);
        requestDto.setContent(content);

        Todo todo = new Todo(requestDto, userDetails);
        todo.setUsername(username);

        TodoService todoService = new TodoService(todoRepository, commentService);

        given(todoRepository.findById(id)).willReturn(Optional.of(todo));
        given(userDetails.getUsername()).willReturn(username);


        String title1 = "제목1";
        String content1 = "내용1";
        TodoRequestDto requestDto1 = new TodoRequestDto();
        requestDto1.setTitle(title1);
        requestDto1.setContent(content1);

        //when
        TodoResponseDto result = todoService.updateTodo(id, requestDto1, userDetails);

        //then
        assertEquals(title1, result.getTitle());
        assertEquals(content1, result.getContent());

    }

    @Test
    @DisplayName("할일카드 수정 실패")
    void Test5() {
        //given
        Long id = 100L;
        String title = "제목";
        String content = "내용";
        String username = "yejin";
        TodoRequestDto requestDto = new TodoRequestDto();
        requestDto.setTitle(title);
        requestDto.setContent(content);

        Todo todo = new Todo(requestDto, userDetails);
        todo.setUsername("yejinyejin");

        TodoService todoService = new TodoService(todoRepository, commentService);

        given(todoRepository.findById(id)).willReturn(Optional.of(todo));
        given(userDetails.getUsername()).willReturn(username);


        String title1 = "제목1";
        String content1 = "내용1";
        TodoRequestDto requestDto1 = new TodoRequestDto();
        requestDto1.setTitle(title1);
        requestDto1.setContent(content1);

        //when
        Exception exception = assertThrows(RejectedExecutionException.class, ()->{
            todoService.updateTodo(id, requestDto1, userDetails);
        });

        //then
        assertEquals("할일카드의 작성자만 수정이 가능합니다.", exception.getMessage());
    }
}
