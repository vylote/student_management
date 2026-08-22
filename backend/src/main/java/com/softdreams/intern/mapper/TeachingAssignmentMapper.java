package com.softdreams.intern.mapper;

import com.softdreams.intern.dto.response.TeachingAssignmentResponse;
import com.softdreams.intern.entity.TeachingAssignment;

import java.util.List;

public interface TeachingAssignmentMapper {
    TeachingAssignmentResponse toResponse(TeachingAssignment teachingAssignment);

    List<TeachingAssignmentResponse> toResponses(List<TeachingAssignment> teachingAssignment);
}
