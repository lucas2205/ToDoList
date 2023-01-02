package com.todolist.ensolvers.services;

import com.todolist.ensolvers.dto.request.CategoryRequestDto;
import com.todolist.ensolvers.dto.response.CategoryResponseDto;

import java.util.List;

public interface ICategoryService {

    CategoryResponseDto save(CategoryRequestDto categoryDto, Long noteId, String token);
    CategoryResponseDto findById(Long id, String token);
    List<CategoryResponseDto> viewAllCategory(String token);

}
