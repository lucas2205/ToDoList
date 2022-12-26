package com.todolist.ensolvers.services.impl;

import com.todolist.ensolvers.dto.request.NotesRequestDto;
import com.todolist.ensolvers.dto.response.NotesResponseDto;
import com.todolist.ensolvers.exception.ResourceNotFoundException;
import com.todolist.ensolvers.mapper.ModelMapperFacade;
import com.todolist.ensolvers.model.Notes;
import com.todolist.ensolvers.repository.ICategoryRepository;
import com.todolist.ensolvers.repository.INotesRepository;
import com.todolist.ensolvers.services.INotesService;
import com.todolist.ensolvers.util.MessageHandler;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class NotesServiceImpl implements INotesService {

    private final INotesRepository notesRepository;

    private final ICategoryRepository categoryRepository;
    private final MessageHandler messageHandler;

    @Override
    public NotesResponseDto save(NotesRequestDto notesDto) {

       return ModelMapperFacade.map(
                notesRepository.save(ModelMapperFacade.map(notesDto, Notes.class)),
                NotesResponseDto.class);
    }

    @Override
    public NotesResponseDto  update(NotesRequestDto notesDto) {

        return notesRepository.findById(notesDto.getId())
                .map(c -> ModelMapperFacade.map(
                        notesRepository.save(ModelMapperFacade.map(notesDto, Notes.class)),
                        NotesResponseDto.class))
                .orElseThrow(() -> new ResourceNotFoundException(messageHandler.noteNotFound));
    }

    @Override
    public boolean delete(Long id) {

        Notes note = notesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(messageHandler.noteNotFound));

        note.getCategories().forEach(category -> category.removeNotes(id));

        notesRepository.delete(note);
        return true;
    }

    @Override
    public NotesResponseDto findById(Long id){

        Notes note = notesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(messageHandler.noteNotFound));
        return ModelMapperFacade.map(note,NotesResponseDto.class);
    }

    @Override
    public  List<NotesResponseDto> viewAllNotesArchived() {
        List<NotesResponseDto> notesArchivedDto = new ArrayList<>();
        notesRepository.findByArchive(true)
                .forEach(note -> notesArchivedDto.add(ModelMapperFacade.map(
                        note, NotesResponseDto.class)));
        return notesArchivedDto;
    }
        @Override
        public List<NotesResponseDto> viewAllNotesNotArchived() {
            List<NotesResponseDto> notesNotArchivedDto = new ArrayList<>();
            notesRepository.findByArchive(false)
                    .forEach(note -> notesNotArchivedDto.add(ModelMapperFacade.map(
                            note, NotesResponseDto.class)));
            return notesNotArchivedDto;
        }
    }