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
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryRequestDto categoryDto, @PathVariable(name="id") Long id) {
        return responseBuilder(HttpStatus.CREATED, categoryService.save(categoryDto,id));
    }

    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        return responseBuilder(HttpStatus.CREATED, categoryService.viewAllCategory());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable(name="id") Long id) {
        return responseBuilder(HttpStatus.CREATED, categoryService.findById(id));
    }
}
