package com.todolist.ensolvers.services.impl;

import com.todolist.ensolvers.dto.NotesDto;
import com.todolist.ensolvers.exception.ResourceNotFoundException;
import com.todolist.ensolvers.mapper.ModelMapperFacade;
import com.todolist.ensolvers.model.Notes;
import com.todolist.ensolvers.repository.ICategoryRepository;
import com.todolist.ensolvers.repository.INotesRepository;
import com.todolist.ensolvers.services.INotesService;
import com.todolist.ensolvers.util.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotesServiceImpl implements INotesService {

    @Autowired
    INotesRepository notesRepository;

    @Autowired
    ICategoryRepository categoryRepository;

    @Autowired
    MessageHandler messageHandler;

    @Override
    public NotesDto save(NotesDto notesDto) {

       return ModelMapperFacade.map(
                notesRepository.save(ModelMapperFacade.map(notesDto, Notes.class)),
                NotesDto.class);
    }

    @Override
    public NotesDto update(NotesDto notesDto) {

        return notesRepository.findById(notesDto.getId())
                .map(c -> ModelMapperFacade.map(
                        notesRepository.save(ModelMapperFacade.map(notesDto, Notes.class)),
                        NotesDto.class))
                .orElseThrow(() -> new ResourceNotFoundException(messageHandler.noteNotFound));
    }

    @Override
    public boolean delete(Long id) {

        Notes note = notesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(messageHandler.noteNotFound));

        note.getCategories().stream().forEach(category -> category.removeNotes(id));

        notesRepository.delete(note);
        return true;
    }

    @Override
    public NotesDto findById(Long id){

        Notes note = notesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(""));
        return ModelMapperFacade.map(note,NotesDto.class);
    }

    @Override
    public  List<NotesDto> viewAllNotesArchived() {
        List<NotesDto> notesArchivedDto = new ArrayList<>();
        notesRepository.findByArchive(true)
                .stream()
                .forEach(note -> notesArchivedDto.add(ModelMapperFacade.map(
                        note, NotesDto.class)));
        return notesArchivedDto;
    }
        @Override
        public List<NotesDto> viewAllNotesNotArchived() {
            List<NotesDto> notesNotArchivedDto = new ArrayList<>();
            notesRepository.findByArchive(false)
                    .stream()
                    .forEach(note -> notesNotArchivedDto.add(ModelMapperFacade.map(
                            note, NotesDto.class)));
            return notesNotArchivedDto;
        }
    }