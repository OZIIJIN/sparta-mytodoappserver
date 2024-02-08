package com.sparta.todoparty.service;

import com.sparta.todoparty.dto.TodoListResponseDto;
import com.sparta.todoparty.dto.TodoRequestDto;
import com.sparta.todoparty.dto.TodoResponseDto;
import com.sparta.todoparty.dto.UserDto;
import com.sparta.todoparty.entity.Todo;
import com.sparta.todoparty.entity.User;
import com.sparta.todoparty.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoResponseDto postTodo(TodoRequestDto todoRequestDto, User user){
        Todo todo = new Todo(todoRequestDto);
        todo.setUser(user);
        todoRepository.save(todo);
        return new TodoResponseDto(todo);
    }

    public TodoResponseDto getTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 할일 id 입니다."));

        return new  TodoResponseDto(todo);
    }

    public Map<UserDto, List<TodoResponseDto>> getUserTodoMap() {
        Map<UserDto, List<TodoResponseDto>> userTodoMap = new HashMap<>();

        //날짜기준으로 내림차순
        List<Todo> todoList = todoRepository.findAll(Sort.by(Sort.Direction.DESC, "createDate"));

        //user단위로 쪼개서 TodoResponseDto에 넣어줘야함
        todoList.forEach(todo -> {
            var userDto = new UserDto(todo.getUser());//todo를 작성한 유저를 조회하고 usertodoMap에 put
            var todoDto = new TodoResponseDto(todo);
            if(userTodoMap.containsKey(userDto)){
                //유저 할일 목록에 항목을 새로 추가
                userTodoMap.get(userDto).add(todoDto);
            }else{
                //유저 할일 목록을 새로 추가
                userTodoMap.put(userDto, new ArrayList<>(List.of(todoDto)));
            }
        });
        return userTodoMap;
    }
}
