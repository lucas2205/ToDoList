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
@AllArgsConstructor
public class NotesController {

    private final INotesService notesService;

    @ApiOperation("Create new note for user")
    @PostMapping
    public ResponseEntity<?> createNote(@Valid @RequestBody NotesRequestDto notesDto, @RequestHeader("Authorization") String token) {
       // return responseBuilder(HttpStatus.CREATED, notesService.save(notesDto, token));
        return new ResponseEntity<>( notesService.save(notesDto, token),HttpStatus.CREATED);
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
        //return responseBuilder(HttpStatus.OK,notesService.viewAllNotesArchived(token));
        return new ResponseEntity<>( notesService.viewAllNotesArchived(token),HttpStatus.OK);
    }

    @ApiOperation("Listar notas no archivadas del usuario")
    @GetMapping()
    public ResponseEntity<?> listNotArchivedNotes(@RequestHeader("Authorization") String token) {
        //return responseBuilder(HttpStatus.OK,notesService.viewAllNotesNotArchived(token));
        return new ResponseEntity<>( notesService.viewAllNotesNotArchived(token),HttpStatus.OK);
    }

    @GetMapping("/allNotes")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>( notesService.findAll(),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id, @RequestHeader("Authorization") String token){
        if (notesService.delete(id, token)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
