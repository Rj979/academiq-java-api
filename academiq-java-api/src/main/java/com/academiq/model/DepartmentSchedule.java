package com.academiq.model;

import jakarta.persistence.*;

@Entity
@Table(name = "department_schedule")
public class DepartmentSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String department; // e.g., CSE, ECE, ME

    @Lob
    @Column(nullable = false)
    private String scheduleJson; // { monday:[..], ... }

    public DepartmentSchedule() {}

    public DepartmentSchedule(String department, String scheduleJson) {
        this.department = department;
        this.scheduleJson = scheduleJson;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getScheduleJson() { return scheduleJson; }
    public void setScheduleJson(String scheduleJson) { this.scheduleJson = scheduleJson; }
}


