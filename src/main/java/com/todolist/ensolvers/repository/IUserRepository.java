package com.todolist.ensolvers.repository;

import java.util.Optional;

import com.todolist.ensolvers.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IUserRepository extends JpaRepository<Users, Long>{

    public Optional<Users> findByEmail(String email);

    public Optional<Users> findByUsernameOrEmail(String username, String email);

    public Optional<Users> findByUsername(String username);

    public Boolean existsByUsername(String username);

    public Boolean existsByEmail(String email);

}