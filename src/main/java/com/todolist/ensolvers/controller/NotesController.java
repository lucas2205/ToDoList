package com.todolist.ensolvers.controller;

import com.todolist.ensolvers.dto.NotesDto;
import com.todolist.ensolvers.services.INotesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/notes")
@CrossOrigin(origins = "http://localhost:4200")
public class NotesController {

    @Autowired
    INotesService notesService;

    @PostMapping
    public ResponseEntity<?> createNote(@Valid @RequestBody NotesDto notesDto) {
        return new ResponseEntity<>(notesService.save(notesDto), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody NotesDto notesDto) {
        notesDto.setId(id);
        return new ResponseEntity<>(notesService.update(notesDto), HttpStatus.OK);
    }

    @GetMapping("/archive")
    public ResponseEntity<?> listArchivedNotes() {
        return new ResponseEntity<>(notesService.viewAllNotesArchived(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> listNotArchivedNotes() {
        return new ResponseEntity<>(notesService.viewAllNotesNotArchived(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return new ResponseEntity<>(notesService.findById(id), HttpStatus.OK);
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
