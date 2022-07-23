package com.todolist.ensolvers.services;

import com.todolist.ensolvers.dto.CategoryDto;

import java.util.List;

public interface ICategoryService {

    CategoryDto save(CategoryDto categoryDto, Long noteId);

    CategoryDto findById(Long id);
    List<CategoryDto> viewAllCategory();

}
