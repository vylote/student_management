package com.softdreams.intern.mapper;

import com.softdreams.intern.dto.response.TeachingAssignmentResponse;
import com.softdreams.intern.entity.TeachingAssignment;

public interface TeachingAssignmentMapper {
    TeachingAssignmentResponse toResponse(TeachingAssignment teachingAssignment);
}
