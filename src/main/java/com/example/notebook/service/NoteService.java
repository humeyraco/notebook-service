package com.example.notebook.service;

import com.example.notebook.entity.Note;
import com.example.notebook.entity.User;
import com.example.notebook.exception.CommonException;
import com.example.notebook.exception.ErrorCode;
import com.example.notebook.interfaces.dto.NoteDto;
import com.example.notebook.mapper.NoteMapper;
import com.example.notebook.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserService userService;

    public void addNote(NoteDto noteDto) {
        Note note = NoteMapper.INSTANCE.mapToNote(noteDto);
        User user = userService.getAuthenticatedUser();
        note.setUser(user);
        noteRepository.save(note);
    }

    public List<NoteDto> listNotes() {
        return NoteMapper.INSTANCE.mapToNoteDtoList(noteRepository.findAll());
    }

    public void updateNote(Long id, NoteDto noteDto) {
        Note note = noteRepository.findById(id).orElseThrow(() -> CommonException.getCommonException(ErrorCode.NOTE_NOT_FOUND));
        userService.checkIfUserSame(note.getUser().getId());
        note.setTitle(noteDto.getTitle());
        note.setContent(noteDto.getContent());
        noteRepository.save(note);
    }

    public void deleteNote(Long id) {
        Note note = noteRepository.findById(id).orElseThrow(() -> CommonException.getCommonException(ErrorCode.NOTE_NOT_FOUND));
        userService.checkIfUserSame(note.getUser().getId());
        noteRepository.deleteById(id);
    }




    public List<NoteDto> listNotesByUserId() {
        User user =  userService.getAuthenticatedUser();
        return NoteMapper.INSTANCE.mapToNoteDtoList(noteRepository.findByUser(user));
    }
}