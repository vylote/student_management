package com.softdreams.intern.repository;

import com.softdreams.intern.entity.TeachingAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeachingAssigmentRepository extends JpaRepository<TeachingAssignment, Long> {

    boolean existsBySubjectIdAndClassroom(Long subjectId, String classroom);

    Optional<TeachingAssignment> findBySubjectIdAndClassroom(Long subjectId, String classroom);
}
