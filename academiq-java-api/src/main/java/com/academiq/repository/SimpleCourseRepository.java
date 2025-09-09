package com.academiq.repository;

import com.academiq.model.SimpleCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SimpleCourseRepository extends JpaRepository<SimpleCourse, Long> {
    List<SimpleCourse> findByDepartment(String department);
    List<SimpleCourse> findBySemester(String semester);
}


