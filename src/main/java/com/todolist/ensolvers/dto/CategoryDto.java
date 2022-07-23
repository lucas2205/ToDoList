package com.todolist.ensolvers.dto;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Setter
@Getter
public class CategoryDto {

    private Long id;

    private String name;

    private List<NotesDto> notes;

}
