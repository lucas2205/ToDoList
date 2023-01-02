package com.todolist.ensolvers.exception;

public class UserUnauthorizedException extends RuntimeException{
    public UserUnauthorizedException(String message){
        super(message);
    }
}
