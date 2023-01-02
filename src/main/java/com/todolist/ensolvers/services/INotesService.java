package com.todolist.ensolvers.services;


import com.todolist.ensolvers.dto.request.NotesRequestDto;
import com.todolist.ensolvers.dto.response.NotesResponseDto;


import java.util.List;

public interface INotesService {
    NotesResponseDto save(NotesRequestDto notesDto, String token);

    NotesResponseDto update(NotesRequestDto notesDto, String token);

    boolean delete(Long id);

    NotesResponseDto findById(Long id);

    List<NotesResponseDto> viewAllNotesArchived(String token);

    List<NotesResponseDto> viewAllNotesNotArchived(String token);
}
