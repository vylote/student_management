package com.softdreams.intern.mapper.impl;

import com.softdreams.intern.dto.response.TeachingAssignmentResponse;
import com.softdreams.intern.entity.TeachingAssignment;
import com.softdreams.intern.mapper.SubjectMapper;
import com.softdreams.intern.mapper.TeacherMapper;
import com.softdreams.intern.mapper.TeachingAssignmentMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TeachingAssignmentMapperImpl implements TeachingAssignmentMapper {
    final SubjectMapper subjectMapper;

    final TeacherMapper teacherMapper;

    @Override
    public TeachingAssignmentResponse toResponse(TeachingAssignment teachingAssignment) {
        if (teachingAssignment == null)
            return null;

        TeachingAssignmentResponse teachingAssignmentResponse = new TeachingAssignmentResponse();
        teachingAssignmentResponse.setId(teachingAssignment.getId());
        teachingAssignmentResponse.setClassroom(teachingAssignment.getClassroom());
        teachingAssignmentResponse.setSubject(subjectMapper.toResponse(teachingAssignment.getSubject()));
        teachingAssignmentResponse.setTeacher(teacherMapper.toResponse(teachingAssignment.getTeacher()));

        return teachingAssignmentResponse;
    }

    @Override
    public List<TeachingAssignmentResponse> toResponses(List<TeachingAssignment> teachingAssignments) {
        if (teachingAssignments == null)
            return Collections.emptyList();

        List<TeachingAssignmentResponse> teachingAssignmentResponses = new ArrayList<>(teachingAssignments.size());
        for (TeachingAssignment teachingAssignment : teachingAssignments) {
            teachingAssignmentResponses.add(toResponse(teachingAssignment));
        }

        return teachingAssignmentResponses;
    }
}
