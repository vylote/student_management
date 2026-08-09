package com.softdreams.intern.repository;

import com.softdreams.intern.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByUsername(String userName);

    Optional<Account> findByUsername(String userName);
}
