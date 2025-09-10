package com.academiq.repository;

import com.academiq.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    Optional<Student> findByStudentId(String studentId);
    
    Optional<Student> findByEmail(String email);
    
    List<Student> findByDepartment(String department);
    
    List<Student> findBySemester(Integer semester);
    
    boolean existsByStudentId(String studentId);
    
    boolean existsByEmail(String email);
}