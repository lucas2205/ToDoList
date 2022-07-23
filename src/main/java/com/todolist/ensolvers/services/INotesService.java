package com.todolist.ensolvers.services;

import com.todolist.ensolvers.dto.NotesDto;


import java.util.List;

public interface INotesService {
    NotesDto save(NotesDto notesDto);

    NotesDto update(NotesDto notesDto);

    boolean delete(Long id);

    NotesDto findById(Long id);

    List<NotesDto> viewAllNotesArchived();

    List<NotesDto> viewAllNotesNotArchived();
}
