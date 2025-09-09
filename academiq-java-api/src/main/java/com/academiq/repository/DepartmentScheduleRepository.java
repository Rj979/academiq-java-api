package com.academiq.repository;

import com.academiq.model.DepartmentSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentScheduleRepository extends JpaRepository<DepartmentSchedule, Long> {
    Optional<DepartmentSchedule> findByDepartment(String department);
}


