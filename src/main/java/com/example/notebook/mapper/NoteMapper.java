package com.example.notebook.mapper;

import com.example.notebook.entity.Note;
import com.example.notebook.interfaces.dto.NoteDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NoteMapper {

    public static final NoteMapper INSTANCE = Mappers.getMapper(NoteMapper.class);

    @Mapping(target = "username", source = "user.username")
    NoteDto mapToNoteDto(Note note);
    Note mapToNote(NoteDto noteDto);
    List<NoteDto> mapToNoteDtoList(List<Note> notes);
}
