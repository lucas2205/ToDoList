package com.todolist.ensolvers.controller;

import com.todolist.ensolvers.dto.request.NotesRequestDto;
import com.todolist.ensolvers.services.INotesService;

import io.swagger.annotations.ApiOperation;
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

    @ApiOperation("Create new note for user")
    @PostMapping
    public ResponseEntity<?> createNote(@Valid @RequestBody NotesRequestDto notesDto, @RequestHeader("Authorization") String token) {
        return responseBuilder(HttpStatus.CREATED, notesService.save(notesDto, token));
    }

    @ApiOperation("Actualizar notas del usuario")
    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody NotesRequestDto notesDto, @RequestHeader("Authorization") String token) {
        notesDto.setId(id);
        return responseBuilder(HttpStatus.OK, notesService.update(notesDto, token));
    }

    @ApiOperation("Listar notas archivadas del usuario")
    @GetMapping("/archive")
    public ResponseEntity<?> listArchivedNotes(@RequestHeader("Authorization") String token) {
        return responseBuilder(HttpStatus.OK,notesService.viewAllNotesArchived(token));
    }

    @ApiOperation("Listar notas no archivadas del usuario")
    @GetMapping
    public ResponseEntity<?> listNotArchivedNotes(@RequestHeader("Authorization") String token) {
        return responseBuilder(HttpStatus.OK,notesService.viewAllNotesNotArchived(token));
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
