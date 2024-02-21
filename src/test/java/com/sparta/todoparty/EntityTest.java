package com.sparta.todoparty;

import com.sparta.todoparty.dto.CommentRequestDto;
import com.sparta.todoparty.dto.TodoRequestDto;
import com.sparta.todoparty.entity.Comment;
import com.sparta.todoparty.entity.Todo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntityTest {
    @Test
    @DisplayName("Todo update 메서드 - title, content 수정")
    void Test1(){
        //given
        String title = "제목";
        String content = "내용";
        Todo todo = new Todo();
        todo.setTitle(title);
        todo.setContent(content);

        String modifiedTitle = "수정 제목";
        String modifiedContent = "수정 내용";
        TodoRequestDto todoRequestDto = new TodoRequestDto();
        todoRequestDto.setTitle(modifiedTitle);
        todoRequestDto.setContent(modifiedContent);

        //when
        todo.update(todoRequestDto);

        //then
        assertEquals(todo.getTitle(),"수정 제목");
        assertEquals(todo.getContent(), "수정 내용");
    }

    @Test
    @DisplayName("Comment update 메서드 - content 수정")
    void Test2(){
        //given
        String content = "내용";
        Comment comment = new Comment();
        comment.setContent(content);

        String modifiedContent = "수정 내용";
        CommentRequestDto commentRequestDto = new CommentRequestDto();
        commentRequestDto.setContent(modifiedContent);

        //when
        comment.update(commentRequestDto);

        //then
        assertEquals(comment.getContent(),"수정 내용");
    }
}
