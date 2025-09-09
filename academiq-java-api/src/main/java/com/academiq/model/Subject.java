package com.academiq.model;

import jakarta.persistence.*;

@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String subject_code;

    private String subject_name;
    private int credits;

    // Constructors
    public Subject() {}

    public Subject(Long id, String subject_code, String subject_name, int credits) {
        this.id = id;
        this.subject_code = subject_code;
        this.subject_name = subject_name;
        this.credits = credits;
    }

    // Builder pattern
    public static SubjectBuilder builder() {
        return new SubjectBuilder();
    }

    public static class SubjectBuilder {
        private Long id;
        private String subject_code;
        private String subject_name;
        private int credits;

        public SubjectBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public SubjectBuilder subject_code(String subject_code) {
            this.subject_code = subject_code;
            return this;
        }

        public SubjectBuilder subject_name(String subject_name) {
            this.subject_name = subject_name;
            return this;
        }

        public SubjectBuilder credits(int credits) {
            this.credits = credits;
            return this;
        }

        public Subject build() {
            return new Subject(id, subject_code, subject_name, credits);
        }
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject_code() {
        return subject_code;
    }

    public void setSubject_code(String subject_code) {
        this.subject_code = subject_code;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }
}