package com.softdreams.intern.service.impl;

import com.softdreams.intern.dto.request.LoginRequest;
import com.softdreams.intern.dto.response.TokenResponse;
import com.softdreams.intern.entity.Account;
import com.softdreams.intern.entity.HasAccount;
import com.softdreams.intern.entity.Role;
import com.softdreams.intern.exception.AppException;
import com.softdreams.intern.exception.ErrorCode;
import com.softdreams.intern.repository.AccountRepository;
import com.softdreams.intern.repository.RoleRepository;
import com.softdreams.intern.service.AccountService;
import com.softdreams.intern.service.JwtService;
import com.softdreams.intern.util.UsernameGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    final AccountRepository accountRepository;

    final PasswordEncoder passwordEncoder;

    final RoleRepository roleRepository;

    final JwtService jwtService;

    @Transactional
    @Override
    public <T extends HasAccount> T createAccount(
            T entity,
            JpaRepository<T, Long> repository,
            String password,
            String roleCode) {

        if (entity.getAccount() != null) {
            throw new AppException(ErrorCode.ACCOUNT_ALREADY_ASSIGNED);
        }

        String username = UsernameGenerator.generate(entity.getFullName(), entity.getCode());

        if (accountRepository.existsByUsername(username)) {
            throw new AppException(ErrorCode.USERNAME_ALREADY_EXISTS);
        }

        Role role = roleRepository.findByCode(roleCode)
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_EXISTS));

        Account account = new Account();
        account.setUsername(username);
        account.setPassword(passwordEncoder.encode(password));
        account.setRole(role);
        Account savedAccount = accountRepository.save(account);

        entity.setAccount(savedAccount);
        return repository.save(entity);
    }

    @Transactional
    @Override
    public TokenResponse login(LoginRequest request) {
        Account account = accountRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.ACCOUNT_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), account.getPassword())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        String token = jwtService.generateToken(account);

        return TokenResponse.builder()
                .token(token)
                .build();
    }
}
