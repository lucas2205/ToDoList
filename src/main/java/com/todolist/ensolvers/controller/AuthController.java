package com.todolist.ensolvers.controller;


import com.todolist.ensolvers.dto.LoginDto;
import com.todolist.ensolvers.dto.RegisterDto;
import com.todolist.ensolvers.repository.IUserRepository;
import com.todolist.ensolvers.services.IUserService;
import com.todolist.ensolvers.util.MessageHandler;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.todolist.ensolvers.util.ResponseBuilder.responseBuilder;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final IUserRepository userRepository;
    private final IUserService userService;
    private final MessageHandler messageHandler;

    @ApiOperation("Login de usuarios")
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDto loginDto) {
        return responseBuilder(HttpStatus.OK, userService.loginUser(loginDto));
    }

    @ApiOperation("Registro de usuario")
    @PostMapping("/register")
    public ResponseEntity<?> registerUsuario(@RequestBody RegisterDto registerDto){

        if(userRepository.existsByUsername(registerDto.getUsername())){
            return new ResponseEntity<>(messageHandler.userExist, HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(registerDto.getEmail())){
            return new ResponseEntity<>(messageHandler.emailExist,HttpStatus.BAD_REQUEST);
        }

        return responseBuilder(HttpStatus.CREATED, userService.saveUser(registerDto));
    }

}
