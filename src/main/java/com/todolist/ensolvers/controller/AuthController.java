package com.todolist.ensolvers.controller;

import java.util.Collections;

import com.todolist.ensolvers.dto.LoginDto;
import com.todolist.ensolvers.dto.RegisterDto;
import com.todolist.ensolvers.model.Role;
import com.todolist.ensolvers.model.Users;
import com.todolist.ensolvers.repository.IRoleRepository;
import com.todolist.ensolvers.repository.IUserRepository;
import com.todolist.ensolvers.security.JwtAuthResponseDto;
import com.todolist.ensolvers.security.JwtTokenProvider;

import com.todolist.ensolvers.util.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private MessageHandler messageHandler;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDto> authenticateUser(@RequestBody LoginDto loginDto) {


        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),loginDto.getPassword()));


        SecurityContextHolder.getContext().setAuthentication(authentication);


        String token = jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JwtAuthResponseDto(token));


    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUsuario(@RequestBody RegisterDto registerDto){

        if(userRepository.existsByUsername(registerDto.getUsername())){

            return new ResponseEntity<>(messageHandler.userExist,HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(registerDto.getEmail())){

            return new ResponseEntity<>(messageHandler.emailExist,HttpStatus.BAD_REQUEST);
        }

        Users usuario = new Users();
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

        return new ResponseEntity<>(messageHandler.register,HttpStatus.OK);

    }


}
