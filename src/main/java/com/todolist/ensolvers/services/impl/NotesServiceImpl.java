package com.todolist.ensolvers.services.impl;

import com.todolist.ensolvers.dto.request.NotesRequestDto;
import com.todolist.ensolvers.dto.response.NotesResponseDto;
import com.todolist.ensolvers.exception.ResourceNotFoundException;
import com.todolist.ensolvers.exception.UserUnauthorizedException;
import com.todolist.ensolvers.mapper.ModelMapperFacade;
import com.todolist.ensolvers.model.Notes;
import com.todolist.ensolvers.repository.ICategoryRepository;
import com.todolist.ensolvers.repository.INotesRepository;
import com.todolist.ensolvers.repository.IUserRepository;
import com.todolist.ensolvers.security.JwtTokenProvider;
import com.todolist.ensolvers.services.INotesService;
import com.todolist.ensolvers.util.MessageHandler;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NotesServiceImpl implements INotesService {

    private final INotesRepository notesRepository;
    private final MessageHandler messageHandler;
    private final JwtTokenProvider jwt;

    @Override
    public NotesResponseDto save(NotesRequestDto notesDto, String token) {

        Long userId = jwt.getUserId(token);
        notesDto.setUserId(userId);
       return ModelMapperFacade.map(
                notesRepository.save(ModelMapperFacade.map(notesDto, Notes.class)),
                NotesResponseDto.class);
    }

    @Override
    public NotesResponseDto  update(NotesRequestDto notesDto, String token) {

        Long userId = jwt.getUserId(token);

        if(Objects.equals(notesDto.getUserId(), userId)) {
            return notesRepository.findById(notesDto.getId())
                    .map(c -> ModelMapperFacade.map(
                            notesRepository.save(ModelMapperFacade.map(notesDto, Notes.class)),
                            NotesResponseDto.class))
                    .orElseThrow(() -> new ResourceNotFoundException(messageHandler.noteNotFound));
        }else{
            throw new UserUnauthorizedException("NO AUTORIZADO");
        }
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
    public  List<NotesResponseDto> viewAllNotesArchived(String token) {
        Long userId = jwt.getUserId(token);

        List<NotesResponseDto> notesUserDto = new ArrayList<>();

        notesRepository.findByUser_Id(userId)
                .forEach(note -> notesUserDto.add(ModelMapperFacade.map(
                note, NotesResponseDto.class)));

        return  notesUserDto.stream()
                .filter(NotesResponseDto::isArchive)
                .collect(Collectors.toList());

    }
        @Override
        public List<NotesResponseDto> viewAllNotesNotArchived(String token) {
            Long userId = jwt.getUserId(token);

            List<NotesResponseDto> notesUserDto = new ArrayList<>();

            notesRepository.findByUser_Id(userId)
                    .forEach(note -> notesUserDto.add(ModelMapperFacade.map(
                            note, NotesResponseDto.class)));

            return  notesUserDto.stream()
                    .filter(archive -> !archive.isArchive())
                    .collect(Collectors.toList());
        }
    }