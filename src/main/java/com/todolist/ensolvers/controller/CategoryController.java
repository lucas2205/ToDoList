package com.todolist.ensolvers.controller;

import com.todolist.ensolvers.dto.CategoryDto;
import com.todolist.ensolvers.services.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService;


    @PostMapping("/{id}")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable(name="id") Long id) {
        return new ResponseEntity<>(categoryService.save(categoryDto,id), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        return new ResponseEntity<>(categoryService.viewAllCategory(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(name="id") Long id) {
        return new ResponseEntity<>(categoryService.findById(id), HttpStatus.OK);
    }
}
