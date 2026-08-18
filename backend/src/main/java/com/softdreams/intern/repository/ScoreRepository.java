package com.softdreams.intern.repository;

import com.softdreams.intern.entity.Score;
import com.softdreams.intern.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

    @Query("SELECT s FROM Score s WHERE s.student.id = :studentId AND s.subject.id = :subjectId")
    Optional<Score> findByStudentIdSubjectId(Long studentId, Long subjectId);

    @Query("SELECT s.subject FROM Score s WHERE s.student.id = :studentId")
    Optional<List<Subject>> findSubjectsByStudentId(Long studentId);

    Optional<List<Score>> findByStudentId(Long studentId);

    boolean existsByStudentIdAndSubjectId(Long studentId, Long subjectId);
}
