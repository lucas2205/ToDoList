package com.todolist.ensolvers.dto.request;

import com.todolist.ensolvers.model.Category;
import lombok.Data;

import java.util.Set;

@Data
public class NotesRequestDto {

    private Long id;

    private String title;
    private String content;
    private boolean archive;
    private Set<Category> categories;
}
