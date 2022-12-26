package com.todolist.ensolvers.services.impl;

import com.todolist.ensolvers.dto.request.CategoryRequestDto;
import com.todolist.ensolvers.dto.response.CategoryResponseDto;
import com.todolist.ensolvers.exception.ResourceNotFoundException;
import com.todolist.ensolvers.mapper.ModelMapperFacade;
import com.todolist.ensolvers.model.Category;
import com.todolist.ensolvers.model.Notes;
import com.todolist.ensolvers.repository.ICategoryRepository;
import com.todolist.ensolvers.repository.INotesRepository;
import com.todolist.ensolvers.services.ICategoryService;
import com.todolist.ensolvers.util.MessageHandler;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@AllArgsConstructor
public class CategoryServiceImpl implements ICategoryService {

    private final ICategoryRepository categoryRepository;
    private final INotesRepository notesRepository;
    private final MessageHandler messageHandler;

    @Override
    public CategoryResponseDto save(CategoryRequestDto categoryDto, Long noteId) {

        if(categoryRepository.existsByName(categoryDto.getName())){
            Category category = categoryRepository.findByName(categoryDto.getName());
            addNotes(category,noteId);
            return ModelMapperFacade.map(category, CategoryResponseDto.class);
        }

        Category category = categoryRepository.save(ModelMapperFacade.map(categoryDto, Category.class));
        addNotes(category,noteId);

        return ModelMapperFacade.map(category, CategoryResponseDto.class);
    }

    @Override
    public CategoryResponseDto findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(messageHandler.noteNotFound));

        return ModelMapperFacade.map(category,CategoryResponseDto.class);
    }

    @Override
    public List<CategoryResponseDto> viewAllCategory() {
        List<CategoryResponseDto> categoryDto = new ArrayList<>();

        categoryRepository.findAll()
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
