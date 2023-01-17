package com.todolist.ensolvers.security;


import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import com.todolist.ensolvers.model.Role;
import com.todolist.ensolvers.model.User;
import com.todolist.ensolvers.repository.IUserRepository;
import com.todolist.ensolvers.util.MessageHandler;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException(messageHandler.userNotFound));

        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),mapearRoles(user.getRoles()));
    }

    public User getUserByUsername(String usernameOrEmail)throws UsernameNotFoundException {

    return userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() -> new UsernameNotFoundException(messageHandler.userNotFound));
    }

    private Collection<? extends GrantedAuthority> mapearRoles(Set<Role> roles){
        return roles.stream().map(rol-> new SimpleGrantedAuthority(rol.getName())).collect(Collectors.toList());
    }

}
