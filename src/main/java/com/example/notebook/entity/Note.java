// src/main/java/com/example/notebook/entity/Note.java
package com.example.notebook.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "notes")
@Data
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    @CreationTimestamp
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}