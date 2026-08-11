package com.softdreams.intern.service;

import java.util.List;

public interface RolePermissionCacheService {
    List<String> getPermissions(String roleCode);
    void evictCache(String roleCode);
}
