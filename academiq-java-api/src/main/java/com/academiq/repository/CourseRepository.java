package com.academiq.repository;

import com.academiq.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    
    @Query("SELECT new map(p.id as id, p.code as code, p.name as name, p.category as category, p.credits as credits, p.department as department, c.semester as semester, c.academicYear as academicYear) " +
           "FROM Course c JOIN c.paper p WHERE c.teacher.id = :teacherId")
    List<Map<String, Object>> findPapersByTeacherId(@Param("teacherId") Long teacherId);
}
