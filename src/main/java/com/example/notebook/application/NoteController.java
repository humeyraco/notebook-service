package com.example.notebook.application;

import com.example.notebook.interfaces.dto.NoteDto;
import com.example.notebook.service.NoteService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addNote(@RequestBody @Valid NoteDto noteDto) {
        noteService.addNote(noteDto);
    }

    @GetMapping("/list")
    public List<NoteDto> listNotes() {
        return noteService.listNotes();
    }

    @PutMapping("/{id}")
    public void updateNote(@PathVariable @Valid @NotNull Long id, @RequestBody @Valid NoteDto note) {
        noteService.updateNote(id, note);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNote(@PathVariable  @NotNull Long id) {
        noteService.deleteNote(id);
    }

    @GetMapping("/user-notes")
    public List<NoteDto> listNotesByUserId() {
        return noteService.listNotesByUserId();
    }
}