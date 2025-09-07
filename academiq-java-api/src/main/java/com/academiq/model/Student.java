package com.academiq.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "student")
public class Student {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roll_no;
    private String name;
    private String email;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
