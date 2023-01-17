package com.todolist.ensolvers.services.impl;

import com.todolist.ensolvers.dto.request.NotesRequestDto;
import com.todolist.ensolvers.dto.response.NotesResponseDto;
import com.todolist.ensolvers.exception.ResourceNotFoundException;
import com.todolist.ensolvers.exception.UserUnauthorizedException;
import com.todolist.ensolvers.mapper.ModelMapperFacade;
import com.todolist.ensolvers.model.Notes;
import com.todolist.ensolvers.repository.INotesRepository;
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
    public NotesResponseDto update(Long id, NotesRequestDto notesDto, String token) {

        Long userId = jwt.getUserId(token);
        notesDto.setUserId(userId);
        Notes note = notesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(messageHandler.noteNotFound));


        if(userId == note.getUser().getId()){
            notesRepository.save(ModelMapperFacade.map(notesDto,Notes.class));
            return ModelMapperFacade.map(note, NotesResponseDto.class);
        }else{
            throw new UserUnauthorizedException("NO AUTORIZADO");
        }
    }

    @Override
    public boolean delete(Long id, String token) {

        Long userId = jwt.getUserId(token);

        Notes note = notesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(messageHandler.noteNotFound));

        if(Objects.equals(note.getUser().getId(), userId)) {
        note.getCategories().forEach(category -> category.removeNotes(id));
        notesRepository.delete(note);
        }else{
            throw new UserUnauthorizedException("NO AUTORIZADO");
        }

        return true;
    }

    @Override
    public NotesResponseDto findById(String token, Long id){

        Long userId = jwt.getUserId(token);

        Notes note = notesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(messageHandler.noteNotFound));

        if(userId == note.getUser().getId()){
            return ModelMapperFacade.map(note, NotesResponseDto.class);
        }else{
            throw new UserUnauthorizedException("NO AUTORIZADO");
        }
    }

    @Override
    public List<NotesResponseDto> findAll(){

       List<NotesResponseDto> allNotes = new ArrayList<>();

        notesRepository.findAll()
                .forEach(note -> allNotes.add(ModelMapperFacade.map(
                        note, NotesResponseDto.class)));

       return  allNotes;
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