package com.academiq.model;

import jakarta.persistence.*;

@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String roll_no;

    private String name;

    @Column(unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    // Constructors
    public Student() {}

    public Student(Long id, String roll_no, String name, String email, Course course) {
        this.id = id;
        this.roll_no = roll_no;
        this.name = name;
        this.email = email;
        this.course = course;
    }

    // Builder pattern
    public static StudentBuilder builder() {
        return new StudentBuilder();
    }

    public static class StudentBuilder {
        private Long id;
        private String roll_no;
        private String name;
        private String email;
        private Course course;

        public StudentBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public StudentBuilder roll_no(String roll_no) {
            this.roll_no = roll_no;
            return this;
        }

        public StudentBuilder name(String name) {
            this.name = name;
            return this;
        }

        public StudentBuilder email(String email) {
            this.email = email;
            return this;
        }

        public StudentBuilder course(Course course) {
            this.course = course;
            return this;
        }

        public Student build() {
            return new Student(id, roll_no, name, email, course);
        }
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoll_no() {
        return roll_no;
    }

    public void setRoll_no(String roll_no) {
        this.roll_no = roll_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}