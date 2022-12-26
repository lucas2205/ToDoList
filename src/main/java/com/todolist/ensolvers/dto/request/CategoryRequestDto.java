package com.todolist.ensolvers.dto.request;

import com.todolist.ensolvers.dto.response.NotesResponseDto;
import lombok.Data;

import java.util.List;

@Data
public class CategoryRequestDto {
    private Long id;

    private String name;
    private List<NotesResponseDto> notes;
}
