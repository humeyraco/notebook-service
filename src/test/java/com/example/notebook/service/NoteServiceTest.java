package com.example.notebook.service;

import com.example.notebook.entity.Note;
import com.example.notebook.entity.User;
import com.example.notebook.exception.CommonException;
import com.example.notebook.exception.ErrorCode;
import com.example.notebook.interfaces.dto.NoteDto;
import com.example.notebook.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private UserService userService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private NoteService noteService;

    @Test
    void addNote_savesNoteSuccessfully() {
        NoteDto noteDto = new NoteDto();
        Note note = new Note();
        User user = new User();

        when(userService.getAuthenticatedUser()).thenReturn(user);

        noteService.addNote(noteDto);

        verify(noteRepository, times(1)).save(any());
    }

    @Test
    void addNote_throwsExceptionWhenUserNotFound() {
        NoteDto noteDto = new NoteDto();
        when(userService.getAuthenticatedUser()).thenThrow(CommonException.getCommonException(ErrorCode.USER_NOT_FOUND));

        CommonException exception = assertThrows(CommonException.class, () -> {
            noteService.addNote(noteDto);
        });

        assertEquals(ErrorCode.USER_NOT_FOUND.getCode(), exception.getErrorCode());
    }

    @Test
    void listNotes_returnsListOfNotes() {
        List<Note> notes = List.of(new Note());
        List<NoteDto> noteDtos = List.of(new NoteDto());
        when(noteRepository.findAll()).thenReturn(notes);

        List<NoteDto> result = noteService.listNotes();

        assertEquals(noteDtos, result);
    }

    @Test
    void updateNote_updatesNoteSuccessfully() {
        Long id = 1L;
        NoteDto noteDto = new NoteDto();
        Note note = new Note();
        User user = new User();
        user.setUsername("username");
        note.setUser(user);
        when(noteRepository.findById(id)).thenReturn(Optional.of(note));

        noteService.updateNote(id, noteDto);

        verify(noteRepository, times(1)).save(note);
    }

    @Test
    void updateNote_throwsExceptionWhenNoteNotFound() {
        Long id = 1L;
        NoteDto noteDto = new NoteDto();
        when(noteRepository.findById(id)).thenReturn(Optional.empty());

        CommonException exception = assertThrows(CommonException.class, () -> {
            noteService.updateNote(id, noteDto);
        });

        assertEquals(ErrorCode.NOTE_NOT_FOUND.getCode(), exception.getErrorCode());
    }

    @Test
    void deleteNote_deletesNoteSuccessfully() {
        Long id = 1L;
        Note note = new Note();
        User user = new User();
        user.setUsername("username");
        note.setUser(user);
        when(noteRepository.findById(id)).thenReturn(Optional.of(note));
        noteService.deleteNote(id);

        verify(noteRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteNote_throwsExceptionWhenNoteNotFound() {
        Long id = 1L;
        when(noteRepository.findById(id)).thenReturn(Optional.empty());

        CommonException exception = assertThrows(CommonException.class, () -> {
            noteService.deleteNote(id);
        });

        assertEquals(ErrorCode.NOTE_NOT_FOUND.getCode(), exception.getErrorCode());
    }

    @Test
    void listNotesByUserId_returnsListOfNotes() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        List<Note> notes = List.of(new Note());
        List<NoteDto> noteDtos = List.of(new NoteDto());

        when(userService.getAuthenticatedUser()).thenReturn(user);
        when(noteRepository.findByUser(user)).thenReturn(notes);

        List<NoteDto> result = noteService.listNotesByUserId();

        assertEquals(noteDtos, result);
    }

    @Test
    void listNotesByUserId_throwsExceptionWhenUserNotFound() {

        when(userService.getAuthenticatedUser()).thenThrow(CommonException.getCommonException(ErrorCode.USER_NOT_FOUND));

        CommonException exception = assertThrows(CommonException.class, () -> {
            noteService.listNotesByUserId();
        });

        assertEquals(ErrorCode.USER_NOT_FOUND.getCode(), exception.getErrorCode());
    }
}