package com.todolist.ensolvers.security;


import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import com.todolist.ensolvers.model.Role;
import com.todolist.ensolvers.model.Users;
import com.todolist.ensolvers.repository.IUserRepository;
import com.todolist.ensolvers.util.MessageHandler;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailService implements UserDetailsService{

    private final IUserRepository userRepository;

    private final MessageHandler messageHandler;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

        Users user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException(messageHandler.userNotFound));

        return new User(user.getEmail(),user.getPassword(),mapearRoles(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapearRoles(Set<Role> roles){
        return roles.stream().map(rol-> new SimpleGrantedAuthority(rol.getName())).collect(Collectors.toList());
    }

}
