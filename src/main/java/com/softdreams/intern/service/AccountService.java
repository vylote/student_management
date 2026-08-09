package com.softdreams.intern.service;

import com.softdreams.intern.entity.HasAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountService {

    public <T extends HasAccount> T createAccount(
            T entity,
            JpaRepository<T, Long> repository,
            String password,
            String roleCode);
}
