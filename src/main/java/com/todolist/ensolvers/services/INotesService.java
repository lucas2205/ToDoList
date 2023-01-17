package com.todolist.ensolvers.services;


import com.todolist.ensolvers.dto.request.NotesRequestDto;
import com.todolist.ensolvers.dto.response.NotesResponseDto;


import java.util.List;

public interface INotesService {
    NotesResponseDto save(NotesRequestDto notesDto, String token);

    NotesResponseDto update(Long id, NotesRequestDto noteDto, String token);

    boolean delete(Long id, String token);

    List<NotesResponseDto> findAll();

    NotesResponseDto findById(String token, Long id);

    List<NotesResponseDto> viewAllNotesArchived(String token);

    List<NotesResponseDto> viewAllNotesNotArchived(String token);
}
