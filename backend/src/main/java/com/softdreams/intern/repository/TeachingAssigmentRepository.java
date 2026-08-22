package com.softdreams.intern.repository;

import com.softdreams.intern.entity.Subject;
import com.softdreams.intern.entity.TeachingAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeachingAssigmentRepository extends JpaRepository<TeachingAssignment, Long> {

    boolean existsBySubjectIdAndClassroom(Long subjectId, String classroom);

    Optional<TeachingAssignment> findBySubjectIdAndClassroom(Long subjectId, String classroom);

    Optional<List<TeachingAssignment>> findByTeacherId(Long teacherId);

    @Query("SELECT t.subject FROM TeachingAssignment t WHERE t.teacher.id = :teacherId")
    Optional<List<Subject>> findAllSubjectsByTeacherId(Long teacherId);

    @Query("SELECT t.classroom FROM TeachingAssignment t WHERE t.subject.id = :subjectId AND t.teacher.id = :teacherId")
    Optional<List<String>> findAllClassroomsBySubjectIdAndTeacherId(Long subjectId, Long teacherId);
}
