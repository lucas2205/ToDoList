package com.todolist.ensolvers.services.impl;

import com.todolist.ensolvers.dto.request.CategoryRequestDto;
import com.todolist.ensolvers.dto.response.CategoryResponseDto;
import com.todolist.ensolvers.exception.ResourceNotFoundException;
import com.todolist.ensolvers.exception.UserUnauthorizedException;
import com.todolist.ensolvers.mapper.ModelMapperFacade;
import com.todolist.ensolvers.model.Category;
import com.todolist.ensolvers.model.Notes;
import com.todolist.ensolvers.repository.ICategoryRepository;
import com.todolist.ensolvers.repository.INotesRepository;
import com.todolist.ensolvers.security.JwtTokenProvider;
import com.todolist.ensolvers.services.ICategoryService;
import com.todolist.ensolvers.util.MessageHandler;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Service
@AllArgsConstructor
public class CategoryServiceImpl implements ICategoryService {

    private final ICategoryRepository categoryRepository;
    private final INotesRepository notesRepository;
    private final MessageHandler messageHandler;
    private final JwtTokenProvider jwt;

    @Override
    public CategoryResponseDto save(CategoryRequestDto categoryDto, Long noteId, String token) {

        Long userId = jwt.getUserId(token);
        categoryDto.setUserId(userId);

        if(categoryRepository.existsByName(categoryDto.getName())){ /// Ya existe la categoria
            Category category = categoryRepository.findByName(categoryDto.getName());
            addNotes(category,noteId);
            return ModelMapperFacade.map(category, CategoryResponseDto.class);
        }else {                                                     /// Categoria nueva
            Category category = categoryRepository.save(ModelMapperFacade.map(categoryDto, Category.class));
            addNotes(category, noteId);
            return ModelMapperFacade.map(category, CategoryResponseDto.class);
        }
    }

    @Override
    public CategoryResponseDto findById(Long id, String token) {

        Long userId = jwt.getUserId(token);

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(messageHandler.noteNotFound));

        if(Objects.equals(category.getUserId(), userId)){
            return ModelMapperFacade.map(category,CategoryResponseDto.class);
        }else{
            throw new UserUnauthorizedException("NO AUTORIZADO");
        }

    }

    @Override
    public List<CategoryResponseDto> viewAllCategory(String token) {
        Long userId = jwt.getUserId(token);
        List<CategoryResponseDto> categoryDto = new ArrayList<>();

        categoryRepository.findByUser_Id(userId)
                .forEach(category -> categoryDto.add(ModelMapperFacade.map(
                        category, CategoryResponseDto.class)));

        return categoryDto;
    }

    private void addNotes(Category category,Long noteId){

        Notes note = notesRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException(messageHandler.noteNotFound));

        note.getCategories().add(category);
        notesRepository.save(note);
    }
}
