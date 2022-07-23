package com.todolist.ensolvers.exception;

import com.todolist.ensolvers.util.MessageHandler;

public class ResourceNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 2L;
    private static MessageHandler messageHandler;

    public ResourceNotFoundException(String errorDetails) {
        super( messageHandler.resourceNotFound + " " + errorDetails);
    }

}