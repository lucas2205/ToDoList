package com.todolist.ensolvers.repository;


import java.util.Optional;

import com.todolist.ensolvers.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;



public interface IRoleRepository extends JpaRepository<Role, Long>{

    public Optional<Role> findByName(String nombre);

}
