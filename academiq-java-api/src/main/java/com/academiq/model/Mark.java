package com.academiq.model;

import jakarta.persistence.*;

@Entity
public class Mark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int internal_marks;
    private int semester;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    // Constructors
    public Mark() {}

    public Mark(Long id, int internal_marks, int semester, Student student, Subject subject) {
        this.id = id;
        this.internal_marks = internal_marks;
        this.semester = semester;
        this.student = student;
        this.subject = subject;
    }

    // Builder pattern
    public static MarkBuilder builder() {
        return new MarkBuilder();
    }

    public static class MarkBuilder {
        private Long id;
        private int internal_marks;
        private int semester;
        private Student student;
        private Subject subject;

        public MarkBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public MarkBuilder internal_marks(int internal_marks) {
            this.internal_marks = internal_marks;
            return this;
        }

        public MarkBuilder semester(int semester) {
            this.semester = semester;
            return this;
        }

        public MarkBuilder student(Student student) {
            this.student = student;
            return this;
        }

        public MarkBuilder subject(Subject subject) {
            this.subject = subject;
            return this;
        }

        public Mark build() {
            return new Mark(id, internal_marks, semester, student, subject);
        }
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getInternal_marks() {
        return internal_marks;
    }

    public void setInternal_marks(int internal_marks) {
        this.internal_marks = internal_marks;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}