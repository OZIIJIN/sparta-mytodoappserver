package com.sparta.todoparty.exception;

public class CommentNotFoundException extends IllegalArgumentException{
    public CommentNotFoundException(String message){
        super(message);
    }
}
