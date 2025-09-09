package com.academiq.model;

import jakarta.persistence.*;

@Entity
@Table(name = "time_schedule")
public class TimeSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    // Store schedule as JSON string: {"monday":["--",...], ...}
    @Lob
    @Column(nullable = false)
    private String scheduleJson;

    public TimeSchedule() {}

    public TimeSchedule(Long userId, String scheduleJson) {
        this.userId = userId;
        this.scheduleJson = scheduleJson;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getScheduleJson() { return scheduleJson; }
    public void setScheduleJson(String scheduleJson) { this.scheduleJson = scheduleJson; }
}


