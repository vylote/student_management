package com.softdreams.intern.mapper;

import com.softdreams.intern.dto.response.RoleResponse;
import com.softdreams.intern.entity.Role;

public interface RoleMapper {
    RoleResponse toResponse(Role role);
}
