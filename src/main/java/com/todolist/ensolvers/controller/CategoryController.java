package com.todolist.ensolvers.controller;

import com.todolist.ensolvers.dto.request.CategoryRequestDto;
import com.todolist.ensolvers.services.ICategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.todolist.ensolvers.util.ResponseBuilder.responseBuilder;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {
    private final ICategoryService categoryService;


    @PostMapping("/{id}")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryRequestDto categoryDto, @PathVariable(name="id") Long id, @RequestHeader("Authorization") String token) {
        return responseBuilder(HttpStatus.CREATED, categoryService.save(categoryDto, id, token));
    }

    @GetMapping
    public ResponseEntity<?> getAllCategories(@RequestHeader("Authorization") String token) {
        return responseBuilder(HttpStatus.OK, categoryService.viewAllCategory(token));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(name="id") Long id, @RequestHeader("Authorization") String token) {
        return responseBuilder(HttpStatus.OK, categoryService.findById(id, token));
    }
}
