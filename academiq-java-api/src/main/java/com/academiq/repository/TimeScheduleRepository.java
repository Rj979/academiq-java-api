package com.academiq.repository;

import com.academiq.model.TimeSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TimeScheduleRepository extends JpaRepository<TimeSchedule, Long> {
    Optional<TimeSchedule> findByUserId(Long userId);
}


