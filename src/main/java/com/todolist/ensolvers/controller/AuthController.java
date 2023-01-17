package com.todolist.ensolvers.controller;


import com.todolist.ensolvers.dto.LoginDto;
import com.todolist.ensolvers.dto.RegisterDto;
import com.todolist.ensolvers.model.User;
import com.todolist.ensolvers.repository.IUserRepository;
import com.todolist.ensolvers.security.CustomUserDetailService;
import com.todolist.ensolvers.services.IUserService;
import com.todolist.ensolvers.util.MessageHandler;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static com.todolist.ensolvers.util.ResponseBuilder.responseBuilder;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200", exposedHeaders = "Authorization")
@AllArgsConstructor
public class AuthController {

    private final IUserService userService;
    private final CustomUserDetailService userDetailService;

    @ApiOperation("Login de usuarios")
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDto loginDto) {
        return new ResponseEntity(userService.loginUser(loginDto),HttpStatus.OK);
    }

    @ApiOperation("Registro de usuario")
    @PostMapping("/register")
    public ResponseEntity<?> registerUsuario(@RequestBody RegisterDto registerDto){
        return responseBuilder(HttpStatus.CREATED, userService.saveUser(registerDto));
    }

    @GetMapping("/current-user")
    public User getCurrentUsuario(Principal principal){
        return  userDetailService.getUserByUsername(principal.getName());
    }

}
