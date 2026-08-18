package com.softdreams.intern.mapper.impl;

import com.softdreams.intern.dto.response.RoleResponse;
import com.softdreams.intern.entity.Role;
import com.softdreams.intern.mapper.RoleMapper;
import org.springframework.stereotype.Component;

@Component
public class RoleMapperImpl implements RoleMapper {
    @Override
    public RoleResponse toResponse(Role role) {
        if (role == null)
            return null;

        RoleResponse response = new RoleResponse();

        response.setCode(role.getCode());
        response.setName(role.getName());

        return response;
    }
}
