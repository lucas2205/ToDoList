package com.todolist.ensolvers.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:messages.properties")
public class MessageHandler {

    public final String resourceNotFound;
    public final String noteNotFound;
    public final String userNotFound;
    public final String emailExist;
    public final String userExist;
    public final String register;

    @Autowired
    public MessageHandler(
            @Value("${resource.notFound}") String resourceNotFound,
            @Value("${note.notFound}") String noteNotFound,
            @Value("${email.exist}") String emailExist,
            @Value("${user.exist }") String userExist,
            @Value("${register}") String register,
            @Value("${user.notFound}") String userNotFound) {

        this.noteNotFound= noteNotFound;
        this.resourceNotFound=resourceNotFound;
        this.userNotFound=userNotFound;
        this.emailExist = emailExist;
        this.userExist = userExist;
        this.register = register;

    }
}
