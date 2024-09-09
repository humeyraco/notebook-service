package com.example.notebook.application;

import com.example.notebook.interfaces.dto.NoteDto;
import com.example.notebook.service.NoteService;
import com.example.notebook.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class NoteControllerTest {

    @Mock
    private NoteService noteService;

    @InjectMocks
    private NoteController noteController;

    @Mock
    private UserService userService;

    @Test
    void addNote_createsNoteSuccessfully() {
        NoteDto noteDto = new NoteDto();
        doNothing().when(noteService).addNote(noteDto);
        noteController.addNote(noteDto);

        verify(noteService, times(1)).addNote(noteDto);
    }

    @Test
    void listNotes_returnsListOfNotes() {
        List<NoteDto> notes = List.of(new NoteDto());
        when(noteService.listNotes()).thenReturn(notes);

        List<NoteDto> result = noteController.listNotes();

        assertEquals(notes, result);
    }

    @Test
    void updateNote_updatesNoteSuccessfully() {
        Long id = 1L;
        NoteDto noteDto = new NoteDto();
        doNothing().when(noteService).updateNote(id, noteDto);

        noteController.updateNote(id, noteDto);

        verify(noteService, times(1)).updateNote(id, noteDto);
    }

    @Test
    void deleteNote_deletesNoteSuccessfully() {
        Long id = 1L;
        doNothing().when(noteService).deleteNote(id);

        noteController.deleteNote(id);

        verify(noteService, times(1)).deleteNote(id);
    }

    @Test
    void listNotesByUserId_returnsListOfNotes() {
        Long userId = 1L;
        List<NoteDto> notes = List.of(new NoteDto());
        when(noteService.listNotesByUserId()).thenReturn(notes);

        List<NoteDto> result = noteController.listNotesByUserId();

        assertEquals(notes, result);
    }

}