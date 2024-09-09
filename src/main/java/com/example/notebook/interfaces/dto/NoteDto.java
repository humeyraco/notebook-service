package com.example.notebook.interfaces.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class NoteDto {

    private Long id;
    @NotEmpty(message = "Title cannot be empty")
    private String title;
    @NotEmpty(message = "Content cannot be empty")
    private String content;
    private String username;
}
