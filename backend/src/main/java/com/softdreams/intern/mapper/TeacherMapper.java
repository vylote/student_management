package com.softdreams.intern.mapper;

import com.softdreams.intern.dto.request.CreateTeacherRequest;
import com.softdreams.intern.dto.response.TeacherResponse;
import com.softdreams.intern.entity.Teacher;

import java.util.List;

public interface TeacherMapper {

    Teacher toTeacher(CreateTeacherRequest request);

    TeacherResponse toResponse(Teacher teacher);

    List<TeacherResponse> toResponses(List<Teacher> teachers);
}
