package com.softdreams.intern.repository;

import com.softdreams.intern.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    @Query("SELECT p.code FROM Permission p WHERE p.code = :roleCode")
    Optional<List<Permission>> findAllByRoleCode(String roleCode);
}
