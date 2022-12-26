package com.todolist.ensolvers.controller;

import com.todolist.ensolvers.dto.request.NotesRequestDto;
import com.todolist.ensolvers.services.INotesService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.todolist.ensolvers.util.ResponseBuilder.responseBuilder;

@RestController
@RequestMapping("/notes")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class NotesController {

    private final INotesService notesService;

    @PostMapping
    public ResponseEntity<?> createNote(@Valid @RequestBody NotesRequestDto notesDto) {
        return responseBuilder(HttpStatus.CREATED, notesService.save(notesDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody NotesRequestDto notesDto) {
        notesDto.setId(id);
        return responseBuilder(HttpStatus.OK, notesService.update(notesDto));
    }

    @GetMapping("/archive")
    public ResponseEntity<?> listArchivedNotes() {
        return responseBuilder(HttpStatus.OK,notesService.viewAllNotesArchived());
    }

    @GetMapping
    public ResponseEntity<?> listNotArchivedNotes() {
        return responseBuilder(HttpStatus.OK,notesService.viewAllNotesNotArchived());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return responseBuilder(HttpStatus.OK, notesService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        if (notesService.delete(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
