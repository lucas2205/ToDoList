package com.todolist.ensolvers.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class CategoryResponseDto {

    private Long id;

    private String name;
    private List<NotesResponseDto> notes;
}
