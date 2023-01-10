package com.todolist.ensolvers.services;

import com.todolist.ensolvers.dto.LoginDto;
import com.todolist.ensolvers.dto.RegisterDto;
import com.todolist.ensolvers.security.JwtAuthResponseDto;

public interface IUserService {

    RegisterDto saveUser(RegisterDto registerDto);

    JwtAuthResponseDto loginUser(LoginDto loginDto);
}
