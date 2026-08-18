package com.softdreams.intern.service;

import com.softdreams.intern.dto.request.CreateTeacherRequest;
import com.softdreams.intern.dto.request.TeachingAssignmentRequest;
import com.softdreams.intern.dto.response.TeacherResponse;
import com.softdreams.intern.dto.response.TeachingAssignmentResponse;

public interface TeacherService {
    TeacherResponse addTeacher(CreateTeacherRequest request);

    TeacherResponse createAccount(String code, String password, String roleCode);

    TeachingAssignmentResponse teachingAssign(TeachingAssignmentRequest request);
}
