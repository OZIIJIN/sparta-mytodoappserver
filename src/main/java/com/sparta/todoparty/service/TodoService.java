package com.sparta.todoparty.service;

import com.sparta.todoparty.dto.TodoRequestDto;
import com.sparta.todoparty.dto.TodoResponseDto;
import com.sparta.todoparty.dto.UserDto;
import com.sparta.todoparty.entity.Todo;
import com.sparta.todoparty.entity.User;
import com.sparta.todoparty.repository.TodoRepository;
import com.sparta.todoparty.security.UserDetailsImpl;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RejectedExecutionException;

@Service
public class TodoService {
    private final TodoRepository todoRepository;
    private final CommentService commentService;

    public TodoService(TodoRepository todoRepository, CommentService commentService) {
        this.todoRepository = todoRepository;
        this.commentService = commentService;
    }

    public TodoResponseDto postTodo(TodoRequestDto todoRequestDto, UserDetailsImpl userDetails){
        Todo todo = new Todo(todoRequestDto, userDetails);
        todoRepository.save(todo);
        return new TodoResponseDto(todo);
    }

    public TodoResponseDto getTodoByTodoId(Long todoId) {
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

    //할일카드 수정
    @Transactional
    public TodoResponseDto updateTodo(Long todoId, TodoRequestDto requestDto, UserDetailsImpl userDetails) {
        //TodoRequserDto에 있는 정보랑 실제 DB에 저장된 정보랑 비교
        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id 입니다."));
        if(!userDetails.getUsername().equals(todo.getUsername())){
            throw new RejectedExecutionException("할일카드의 작성자만 수정이 가능합니다.");
        }
        todo.update(requestDto);

        return new TodoResponseDto(todo);
    }

    @Transactional
    public TodoResponseDto completeTodo(Long todoId, User user) {
        Todo todo = getTodoByTodoId(todoId, user);
        todo.complete(); //완료처리

        return new TodoResponseDto(todo);
    }

    private Todo getTodoByTodoId(Long todoId, User user) {
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Id 입니다."));

        if(!user.getId().equals(todo.getId())){
            throw new RejectedExecutionException("작성자만 수정할 수 있습니다.");
        }
        return todo;
    }

    //유저의 전체 할일카드 조회
    public List<TodoResponseDto> getTodosByUserId(UserDetailsImpl userDetails) {
        List<Todo> todos = todoRepository.findByUserId(userDetails.getUser().getId());
        List<TodoResponseDto> responseDtoList = new ArrayList<>();

        for (Todo todo : todos) {
            responseDtoList.add(new TodoResponseDto(todo));
        }

        return responseDtoList;
    }

    public void deleteTodo(Long todoId, UserDetailsImpl userDetails) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 할일카드 입니다."));
        if(!userDetails.getUsername().equals(todo.getUsername())){
            throw new RejectedExecutionException("게시물의 작성자만 삭제가 가능합니다.");
        }
        commentService.deleteCommentByTodoId(todoId);
        todoRepository.delete(todo);
    }
}
