package com.academiq.repository;

import com.academiq.model.Paper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PaperRepository extends JpaRepository<Paper, Long> {
    
    @Query("SELECT new map(p.id as id, p.code as code, p.name as name, p.category as category, p.credits as credits, p.department as department) FROM Paper p")
    List<Map<String, Object>> findAllPapersWithDetails();
    
    List<Paper> findByDepartment(String department);
    
    List<Paper> findByCategory(String category);
    
    Paper findByCode(String code);
}
