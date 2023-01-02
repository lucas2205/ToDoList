package com.todolist.ensolvers.dto.request;

import com.todolist.ensolvers.dto.response.NotesResponseDto;
import com.todolist.ensolvers.model.User;
import lombok.Data;

import java.util.List;

@Data
public class CategoryRequestDto {
    private Long id;

    private String name;
    private List<NotesResponseDto> notes;
    private Long userId;
}
