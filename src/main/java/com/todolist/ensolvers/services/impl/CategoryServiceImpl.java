package com.todolist.ensolvers.services.impl;

import com.todolist.ensolvers.dto.CategoryDto;
import com.todolist.ensolvers.dto.NotesDto;
import com.todolist.ensolvers.exception.ResourceNotFoundException;
import com.todolist.ensolvers.mapper.ModelMapperFacade;
import com.todolist.ensolvers.model.Category;
import com.todolist.ensolvers.model.Notes;
import com.todolist.ensolvers.repository.ICategoryRepository;
import com.todolist.ensolvers.repository.INotesRepository;
import com.todolist.ensolvers.services.ICategoryService;
import com.todolist.ensolvers.util.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private ICategoryRepository categoryRepository;

    @Autowired
    private INotesRepository notesRepository;

    @Autowired
    MessageHandler messageHandler;

    @Override
    public CategoryDto save(CategoryDto categoryDto, Long noteId) {

        if(categoryRepository.existsByName(categoryDto.getName())){
            Category category = categoryRepository.findByName(categoryDto.getName());
            addNotes(category,noteId);
            return ModelMapperFacade.map(category, CategoryDto.class);
        }

        Category category = categoryRepository.save(ModelMapperFacade.map(categoryDto, Category.class));
        addNotes(category,noteId);

        return ModelMapperFacade.map(category, CategoryDto.class);
    }

    @Override
    public CategoryDto findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(messageHandler.noteNotFound));

        return ModelMapperFacade.map(category,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> viewAllCategory() {
        List<CategoryDto> categoryDto = new ArrayList<>();

        categoryRepository.findAll()
                .stream()
                .forEach(category -> categoryDto.add(ModelMapperFacade.map(
                        category, CategoryDto.class)));
        return categoryDto;
    }

    private void addNotes(Category category,Long noteId){
        Notes note = notesRepository.findById(noteId).get();

        note.getCategories().add(category);
        notesRepository.save(note);
    }
}
