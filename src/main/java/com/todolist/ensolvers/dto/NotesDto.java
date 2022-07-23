package com.todolist.ensolvers.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.todolist.ensolvers.model.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Setter
@Getter
public class NotesDto {
    private Long id;

    private String title;
    private String content;
    private boolean archive;
    private Set<Category> categories;
}
