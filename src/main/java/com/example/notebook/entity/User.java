package com.example.notebook.entity;

import com.example.notebook.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String email;
    private String name;
    private String surname;
    @Enumerated(EnumType.ORDINAL)
    private UserRole role;
    @CreatedDate
    @CreationTimestamp
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

}
