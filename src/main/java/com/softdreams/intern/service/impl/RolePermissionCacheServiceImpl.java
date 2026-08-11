package com.softdreams.intern.service.impl;

import com.softdreams.intern.entity.Permission;
import com.softdreams.intern.exception.AppException;
import com.softdreams.intern.exception.ErrorCode;
import com.softdreams.intern.repository.PermissionRepository;
import com.softdreams.intern.service.RolePermissionCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class RolePermissionCacheServiceImpl implements RolePermissionCacheService {

    final RedisTemplate<Object, Object> redisTemplate;
    static final String ROLE_PERMISSION_PREFIX = "auth:permissions:role:";
    final PermissionRepository repository;

    @Override
    public List<String> getPermissions(String roleCode) {
        String cacheKey = ROLE_PERMISSION_PREFIX + roleCode;

        List<String> permissions = (List<String>) redisTemplate.opsForValue().get(cacheKey);

        if (permissions == null) {
            log.info("Cache miss cho Key: {}. Đang query Database...", cacheKey);
            permissions = fetchPermissionsFromDB(roleCode);

            redisTemplate.opsForValue().set(cacheKey, permissions, 24, TimeUnit.HOURS);
        }
        return permissions;
    }

    @Override
    public void evictCache(String roleCode) {
        redisTemplate.delete(ROLE_PERMISSION_PREFIX + roleCode);
        log.info("Đã xóa cache phân quyền của Role: {}", roleCode);
    }

    private List<String> fetchPermissionsFromDB(String roleCode) {
        List<Permission> permissions =repository.findAllByRoleCode(roleCode)
                .orElseThrow(() -> new AppException(ErrorCode.PERMISSION_NOT_FOUND));

        List<String> permissionCodes = new ArrayList<>();
        for (Permission permission : permissions) {
            permissionCodes.add(permission.getCode());
        }
        return permissionCodes;
    }
}
