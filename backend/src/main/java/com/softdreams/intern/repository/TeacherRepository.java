package com.softdreams.intern.repository;

import com.softdreams.intern.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long>, JpaSpecificationExecutor<Teacher> {
    boolean existsByCode(String code);

    Optional<Teacher> findByCode(String code);

    Optional<Teacher> findByAccountId(long accountId);
}