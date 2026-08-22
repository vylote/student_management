package com.softdreams.intern.service;

import com.softdreams.intern.dto.request.CreateTeacherRequest;
import com.softdreams.intern.dto.request.StudentsListRequest;
import com.softdreams.intern.dto.request.TeacherSearchRequest;
import com.softdreams.intern.dto.request.TeachingAssignmentRequest;
import com.softdreams.intern.dto.response.*;

import java.util.List;

public interface TeacherService {
    TeacherResponse addTeacher(CreateTeacherRequest request);

    TeacherResponse createAccount(String code, String password, String roleCode);

    TeachingAssignmentResponse teachingAssign(TeachingAssignmentRequest request);

    List<SubjectResponse> getMySubjects();

    List<String> getMyClassrooms(Long subjectId);

    PageResponse<StudentResponse> getStudentsInClass(StudentsListRequest request);

    PageResponse<TeacherResponse> searchTeachers(TeacherSearchRequest request);
}