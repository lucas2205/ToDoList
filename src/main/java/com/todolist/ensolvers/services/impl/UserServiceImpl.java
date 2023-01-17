package com.todolist.ensolvers.services.impl;

import com.todolist.ensolvers.dto.LoginDto;
import com.todolist.ensolvers.dto.RegisterDto;
import com.todolist.ensolvers.exception.UserUnauthorizedException;
import com.todolist.ensolvers.model.Role;
import com.todolist.ensolvers.model.User;
import com.todolist.ensolvers.repository.IRoleRepository;
import com.todolist.ensolvers.repository.IUserRepository;
import com.todolist.ensolvers.security.JwtAuthResponseDto;
import com.todolist.ensolvers.security.JwtTokenProvider;
import com.todolist.ensolvers.services.IUserService;

import lombok.AllArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {

    private final AuthenticationManager authenticationManager;
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public RegisterDto saveUser(RegisterDto registerDto){

        if(userRepository.existsByUsername(registerDto.getUsername()) || userRepository.existsByEmail(registerDto.getEmail())){
            throw new UserUnauthorizedException("El Usuario ya está registrado");
        }

        User usuario = new User();
        usuario.setName(registerDto.getName());
        usuario.setUsername(registerDto.getUsername());
        usuario.setEmail(registerDto.getEmail());
        usuario.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {

            Role role1 = new Role();
            Role role2 = new Role();

            role2.setName("ROLE_USER");
            roleRepository.save(role2);
            role1.setName("ROLE_ADMIN");
            roleRepository.save(role1);
        }

        Role roles = roleRepository.findByName("ROLE_ADMIN").get();

        usuario.setRoles(Collections.singleton(roles));
        userRepository.save(usuario);
        return registerDto;
    }

    @Override
    public JwtAuthResponseDto loginUser(LoginDto loginDto){

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return new JwtAuthResponseDto(token);
    }
}
