package com.softdreams.intern.repository;

import com.softdreams.intern.entity.Score;
import com.softdreams.intern.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

    boolean existsByStudentIdAndSubjectId(Long studentId, Long subjectId);

    @Query("SELECT s.subject FROM Score s WHERE s.student.id = :studentId")
    List<Subject> findSubjectsByStudentId(Long studentId);

    List<Score> findByStudentId(Long studentId);
}
