package com.softdreams.intern.service;

import com.softdreams.intern.dto.request.LoginRequest;
import com.softdreams.intern.dto.response.TokenResponse;
import com.softdreams.intern.entity.HasAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountService {

    <T extends HasAccount> T createAccount(
            T entity,
            JpaRepository<T, Long> repository,
            String password,
            String roleCode);

    TokenResponse login(LoginRequest request);
}
